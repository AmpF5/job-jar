package main

import (
	"context"
	"encoding/json"
	"io"
	"log"
	"os"

	"github.com/AmpF5/job-jar/internal/repository"
	"github.com/AmpF5/job-jar/models"
	"github.com/google/uuid"
	"github.com/jackc/pgx/v5"
	"github.com/joho/godotenv"
	"github.com/natefinch/lumberjack"
	"github.com/streadway/amqp"
)

var (
	InfoLogger  *log.Logger
	ErrorLogger *log.Logger
)

func main() {
	logger := &lumberjack.Logger{
		Filename:   "./logs/app.log",
		MaxSize:    10,
		MaxBackups: 7,
		MaxAge:     1,
		Compress:   true,
	}
	multiOut := io.MultiWriter(os.Stdout, logger)

	InfoLogger = log.New(multiOut, "INFO: ", log.Ldate|log.Ltime|log.Lshortfile)
	ErrorLogger = log.New(multiOut, "ERROR: ", log.Ldate|log.Ltime|log.Lshortfile)

	InfoLogger.Println("Muncher is munching...")

	if err := godotenv.Load(); err != nil {
		ErrorLogger.Fatal(err)
	}

	conn, err := amqp.Dial(os.Getenv("RMQ_CONNECTION_STRING"))
	if err != nil {
		ErrorLogger.Fatal(err)
	}

	defer conn.Close()

	ch, err := conn.Channel()
	if err != nil {
		ErrorLogger.Fatal(err)
	}

	defer ch.Close()

	data, err := ch.Consume(
		"offers.collected",
		"",
		true,
		false,
		false,
		false,
		nil,
	)

	forever := make(chan bool)

	ctx := context.Background()

	dbconn, err := pgx.Connect(ctx, os.Getenv("DB_CONNECTION_STRING"))
	if err != nil {
		ErrorLogger.Fatal(err)
	}
	defer dbconn.Close(ctx)

	dbquery := repository.New(dbconn)

	go func() {
		for d := range data {
			var jsonOffers []models.OfferJson
			err := json.Unmarshal(d.Body, &jsonOffers)
			if err != nil {
				ErrorLogger.Println(err)
			}

			ssToAdd := map[string][]uuid.UUID{}
			csToAdd := map[string][]uuid.UUID{}
			oToAdd := []repository.CreateOfferParams{}

			for _, jo := range jsonOffers {
				// Map data from MQ to database params
				o := jo.ToOfferParams()
				oToAdd = append(oToAdd, *o)

				// Append all skills from offer as skill_snapshots
				for _, rs := range jo.RequiredSkills {
					_, ok := ssToAdd[rs]
					if ok {
						ssToAdd[rs] = append(ssToAdd[rs], o.OfferID)
					} else {
						ssToAdd[rs] = []uuid.UUID{o.OfferID}
					}
				}

				// Append company from offer as a company_snapshot
				_, ok := csToAdd[jo.CompanyName]
				if ok {
					csToAdd[jo.CompanyName] = append(csToAdd[jo.CompanyName], o.OfferID)
				} else {
					csToAdd[jo.CompanyName] = []uuid.UUID{o.OfferID}
				}
			}

			// Handle skill_snapshot
			for name, oIDs := range ssToAdd {
				skillSnapshot, err := dbquery.GetSkillSnapshotByName(ctx, name)
				if err != nil {
					InfoLogger.Printf("Skill_snapshot not found %s\n", name)
					// TEMP
					dbquery.CreateSkillSnapshot(ctx, repository.CreateSkillSnapshotParams{
						SkillSnapshotID: uuid.New(),
						Name:            name,
						OfferIds:        oIDs,
					})
				} else {
					ErrorLogger.Println(err)
					// If skill_snapshot exists, update offers_ids
					updateSS := repository.UpdateSkillSnapshotParams{
						SkillSnapshotID: skillSnapshot.SkillSnapshotID,
						OfferIds:        append(oIDs, skillSnapshot.OfferIds...),
					}
					err := dbquery.UpdateSkillSnapshot(ctx, updateSS)
					if err != nil {
						ErrorLogger.Println(err)
					}
				}

				// TODO add handling skills
				// _, err := dbquery.GetByVariant(ctx, name)
				// if err != nil && err != pgx.ErrNoRows {
				// 	fmt.Println("Skill not found in skills")
				// 	fmt.Println(err)
				// } else {
				// }
			}

			// Handle company_snapshot
			for name, oIDs := range csToAdd {
				companySnapshot, err := dbquery.GetCompanySnapshotByName(ctx, name)
				if err != nil {
					InfoLogger.Printf("Company_snapshot not found %s\n", name)
					dbquery.CreateCompanySnapshot(ctx, repository.CreateCompanySnapshotParams{
						CompanySnapshotID: uuid.New(),
						Name:              name,
						OfferIds:          oIDs,
					})
				} else {
					updateC := repository.UpdateCompanySnapshotParams{
						CompanySnapshotID: companySnapshot.CompanySnapshotID,
						OfferIds:          append(oIDs, companySnapshot.OfferIds...),
					}
					err := dbquery.UpdateCompanySnapshot(ctx, updateC)
					if err != nil {
						ErrorLogger.Println(err)
					}
				}

				// TODO add handling companies
			}

			n, err := dbquery.CreateOffer(ctx, oToAdd)
			InfoLogger.Printf("Inserted %d offers\n", n)
			if err != nil {
				ErrorLogger.Println(err)
			}

		}
	}()

	<-forever
}
