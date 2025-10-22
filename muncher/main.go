package main

import (
	"context"
	"encoding/json"
	"fmt"
	"os"

	"github.com/AmpF5/job-jar/internal/repository"
	"github.com/AmpF5/job-jar/models"
	"github.com/google/uuid"
	"github.com/jackc/pgx/v5"
	"github.com/joho/godotenv"
	"github.com/streadway/amqp"
)

func main() {
	fmt.Println("Muncher is munching...")
	if err := godotenv.Load(); err != nil {
		fmt.Println(err)
		panic(err)
	}

	conn, err := amqp.Dial(os.Getenv("RMQ_CONNECTION_STRING"))
	if err != nil {
		fmt.Println(err)
		panic(err)
	}

	defer conn.Close()

	ch, err := conn.Channel()
	if err != nil {
		fmt.Println(err)
		panic(err)
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
		fmt.Println(err)
		panic(err)
	}
	defer dbconn.Close(ctx)

	dbquery := repository.New(dbconn)

	go func() {
		for d := range data {
			var jsonOffers []models.OfferJson
			err := json.Unmarshal(d.Body, &jsonOffers)
			if err != nil {
				fmt.Println(err)
			}

			fmt.Println(jsonOffers)

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
			fmt.Println(ssToAdd)
			for name, oIDs := range ssToAdd {
				skillSnapshot, err := dbquery.GetByName(ctx, name)
				if err != nil {
					fmt.Printf("Skill_snapshot not found %s\n", name)
					// TEMP
					dbquery.CreateSkillSnapshot(ctx, repository.CreateSkillSnapshotParams{
						SkillSnapshotID: uuid.New(),
						Name:            name,
						OfferIds:        oIDs,
					})
				} else {
					fmt.Println(err)
					// If skill_snapshot exists, update offers_ids
					updateSS := repository.UpdateSkillSnapshotParams{
						SkillSnapshotID: skillSnapshot.SkillSnapshotID,
						OfferIds:        append(oIDs, skillSnapshot.OfferIds...),
					}
					err := dbquery.UpdateSkillSnapshot(ctx, updateSS)
					if err != nil {
						fmt.Println("Failed to update skill_snapshot offers")
						fmt.Println(err)
					}

					continue
				}

				// TODO
				// _, err := dbquery.GetByVariant(ctx, name)
				// if err != nil && err != pgx.ErrNoRows {
				// 	fmt.Println("Skill not found in skills")
				// 	fmt.Println(err)
				// } else {
				// }
			}

			n, err := dbquery.CreateOffer(ctx, oToAdd)
			fmt.Println(n)
			if err != nil {
				fmt.Println(err)
			}

		}
	}()

	<-forever
}
