package main

import (
	"time"

	"github.com/google/uuid"
)

type Offer struct {
	OfferId         uuid.UUID `json:"offerId"`
	Guid            string    `json:"guid"`
	Title           string    `json:"title"`
	Slug            string    `json:"slug"`
	RequiredSkills  []Skill   `json:"requiredSkills"`
	WorkplaceType   string    `json:"workplaceType"`
	ExperienceLevel string    `json:"experienceLevel"`
	JobSite         string    `json:"jobSite"`
	OfferStatus     string    `json:"offerStatus"`
	MinimalWage     float32   `json:"minimalWage"`
	MaximalWage     float32   `json:"maximalWage"`
	PublishedAt     time.Time `json:"publishedAt"`
	ExpiredAt       time.Time `json:"expiredAt"`
	Company         *Company  `json:"company"`
}
