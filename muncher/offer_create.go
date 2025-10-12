package main

import (
	"time"

	"github.com/google/uuid"
)

type OfferCreate struct {
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

func newOfferCreate(
	externalId uuid.UUID,
	title, slug, companyName, workplaceType, experienceType, jobSite, offerStatus string,
	minalWage, maximalWage float32,
	publishedAt, expiredAt time.Time,
	requiredSkills []string,
) *OfferCreate {
	oc := OfferCreate{
		ExternalId:      externalId,
		Title:           title,
		Slug:            slug,
		CompanyName:     companyName,
		WorkplaceType:   workplaceType,
		ExperienceLevel: experienceType,
		JobSite:         jobSite,
		OfferStatus:     offerStatus,
		MinimalWage:     minalWage,
		MaximalWage:     maximalWage,
		PublishedAt:     publishedAt,
		ExpiredAt:       expiredAt,
		RequiredSkills:  requiredSkills,
	}

	return &oc
}
