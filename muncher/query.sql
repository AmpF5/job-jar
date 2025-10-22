-- :::: OFFER ::::
-- name: CreateOffer :copyfrom
INSERT INTO offers(offer_id, external_id, title, job_site, experience_level, workplace_type, offer_status, company_id, minimal_wage, maximal_wage, slug, expired_at, published_at)
VALUES($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13);

-- :::: SKILL_SNAPSHOTS ::::
-- name: CreateSkillSnapshot :exec
INSERT INTO skill_snapshots(skill_snapshot_id, name, offer_ids)
VALUES($1, $2, $3);

-- name: CreatSkilSnapshots :copyfrom
INSERT INTO skill_snapshots(skill_snapshot_id, name, offer_ids)
VALUES($1, $2, $3);

-- name: GetByName :many
SELECT * FROM skill_snapshots
WHERE (name = ANY (@names::text[]));

-- :::: SKILLS ::::
-- name: GetByVariant :one
SELECT * FROM skills
WHERE ($1 = ANY (variants)) LIMIT 1;

-- name: GetByVariants :many
-- SELECT * FROM skills WHERE($1 = ANY (variants));
SELECT * FROM skills
WHERE variants @> ARRAY[$1];

-- :::: COMPANY_SNAPSHOTS::::
-- name: CreateCompanySnapshot :exec
INSERT INTO company_snapshots(company_snapshot_id, name, offer_ids)
VALUES($1, $2, $3);
