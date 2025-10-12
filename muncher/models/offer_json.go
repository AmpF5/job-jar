package models

import (
	"time"

	"github.com/AmpF5/job-jar/internal/repository"
	"github.com/google/uuid"
	"github.com/jackc/pgx/v5/pgtype"
)

type OfferJson struct {
	ExternalId      uuid.UUID `json:"externalId"`
	Title           string    `json:"title"`
	Slug            string    `json:"slug"`
	CompanyName     string    `json:"companyName"`
	WorkplaceType   string    `json:"workplaceType"`
	ExperienceLevel string    `json:"experienceLevel"`
	JobSite         string    `json:"jobSite"`
	OfferStatus     string    `json:"offerStatus"`
	MinimalWage     float32   `json:"minimalWage"`
	MaximalWage     float32   `json:"maximalWage"`
	PublishedAt     time.Time `json:"publishedAt"`
	ExpiredAt       time.Time `json:"expiredAt"`
	RequiredSkills  []string  `json:"requiredSkills"`
}

func (oj OfferJson) to() repository.Offer {
	return repository.Offer{
		OfferID:         uuid.New(),
		ExternalID:      oj.ExternalId,
		Title:           oj.Title,
		JobSite:         oj.JobSite,
		ExperienceLevel: oj.ExperienceLevel,
		WorkplaceType:   oj.WorkplaceType,
		OfferStatus:     oj.OfferStatus,
		CompanyID:       uuid.UUID{},
		MinimalWage:     oj.MinimalWage,
		MaximalWage:     oj.MaximalWage,
		Slug:            oj.Slug,
		ExpiredAt:       pgtype.Timestamptz{Time: oj.ExpiredAt, Valid: true},
		PublishedAt:     pgtype.Timestamptz{Time: oj.PublishedAt, Valid: true},
	}
}
