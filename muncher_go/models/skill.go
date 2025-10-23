package models

import "github.com/google/uuid"

type Skill struct {
	skillId  uuid.UUID
	name     string
	variants []string
}
