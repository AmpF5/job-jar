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
