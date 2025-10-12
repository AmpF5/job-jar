package main

import "github.com/google/uuid"

type Company struct {
	companyId uuid.UUID
	name      string
}
