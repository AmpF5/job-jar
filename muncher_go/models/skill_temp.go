package models

import "github.com/google/uuid"

type SkillTemp struct {
	name     string
	offerIds []uuid.UUID
}
