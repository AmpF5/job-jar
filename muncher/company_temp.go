package main

import "github.com/google/uuid"

type CompanyTemp struct {
	name      string
	offersIds []uuid.UUID
}
