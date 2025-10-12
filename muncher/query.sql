-- name: InsertOffer :exec
INSERT INTO offers(offer_id, external_id, title, job_site, experience_level, workplace_type, offer_status, company_id, minimal_wage, maximal_wage, slug, expired_at, published_at)
VALUES($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13);
