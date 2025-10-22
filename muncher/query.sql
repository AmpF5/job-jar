-- :::: OFFER ::::
-- name: CreateOffer :copyfrom
INSERT INTO offers(offer_id, external_id, title, job_site, experience_level, workplace_type, offer_status, company_id, minimal_wage, maximal_wage, slug, expired_at, published_at)
VALUES($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13);

-- :::: SKILL_SNAPSHOTS ::::
-- name: CreateSkillSnapshot :exec
INSERT INTO skill_snapshots(skill_snapshot_id, name, offer_ids)
VALUES($1, $2, $3);

-- name: CreateSkillSnapshots :copyfrom
INSERT INTO skill_snapshots(skill_snapshot_id, name, offer_ids)
VALUES($1, $2, $3);

-- name: GetSkillSnapshotsByNames :many
SELECT * FROM skill_snapshots
WHERE (name = ANY (@names::text[]));

-- name: UpdateSkillSnapshot :exec
UPDATE skill_snapshots
SET offer_ids = $2
WHERE skill_snapshot_id = $1;

-- name: GetSkillSnapshotByName :one
SELECT * FROM skill_snapshots
WHERE name = $1 LIMIT 1;

-- :::: SKILLS ::::
-- name: GetSkillByVariant :one
SELECT * FROM skills
WHERE (sqlc.arg('variant')::text = ANY (variants)) LIMIT 1;

-- name: GetSkillByVariants :many
SELECT * FROM skills
WHERE variants @> ARRAY[$1];

-- :::: COMPANY_SNAPSHOTS::::
-- name: CreateCompanySnapshot :exec
INSERT INTO company_snapshots(company_snapshot_id, name, offer_ids)
VALUES($1, $2, $3);

-- name: GetCompanySnapshotByName :one
SELECT * FROM company_snapshots
WHERE name = $1 LIMIT 1;

-- name: UpdateCompanySnapshot :exec
UPDATE company_snapshots
SET offer_ids = $2
WHERE company_snapshot_id = $1;

-- :::: COMPANY :::: 
-- name: CreateCompany :exec
INSERT INTO companies(company_id, name)
VALUES($1, $2);

-- name: GetCompanyByName :one
SELECT * FROM companies
WHERE name = $1 LIMIT 1;
