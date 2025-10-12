package models

import (
	"time"

	"github.com/google/uuid"
)

type Offer struct {
	offerId         uuid.UUID
	externalId      uuid.UUID
	title           string
	slug            string
	requiredSkills  []Skill
	workplaceType   string
	experienceLevel string
	jobSite         string
	offerStatus     string
	minimalWage     float32
	maximalWage     float32
	publishedAt     time.Time
	expiredAt       time.Time
	company         *Company
}

func fromOfferJSON(of OfferJson) *Offer {
	o := Offer{
		offerId:         uuid.UUID{},
		externalId:      of.ExternalId,
		title:           of.Title,
		slug:            of.Slug,
		requiredSkills:  []Skill{},
		workplaceType:   of.WorkplaceType,
		experienceLevel: of.ExperienceLevel,
		jobSite:         of.JobSite,
		offerStatus:     of.OfferStatus,
		minimalWage:     of.MinimalWage,
		maximalWage:     of.MaximalWage,
		publishedAt:     of.PublishedAt,
		expiredAt:       of.ExpiredAt,
		company:         &Company{},
	}

	return &o
}
