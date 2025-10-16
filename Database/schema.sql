BEGIN;

CREATE TABLE skills (
    skill_id uuid PRIMARY KEY,
    name character varying(255) NOT NULL,
    variants text[]
);

CREATE INDEX ON skills USING GIN(variants);

CREATE TABLE skill_snapshots (
    skill_snapshot_id uuid PRIMARY KEY,
    name character varying(255) NOT NULL,
    offer_ids uuid[]
);

CREATE TABLE companies (
    company_id uuid PRIMARY KEY,
    name character varying(255) NOT NULL
);

CREATE TABLE company_snapshots (
    company_snapshot_id uuid PRIMARY KEY,
    name character varying(255) NOT NULL,
    offer_ids uuid[]
);

CREATE TABLE offers (
    offer_id uuid PRIMARY KEY,
    external_id uuid NOT NULL,
    title character varying(255) NOT NULL,
    job_site character varying(255) NOT NULL,
    experience_level character varying(255) NOT NULL,
    workplace_type character varying(255) NOT NULL,
    offer_status character varying(255) NOT NULL,
    company_id uuid NULL,
    minimal_wage real NOT NULL,
    maximal_wage real NOT NULL,
    slug character varying(255) NOT NULL,
    expired_at timestamp(6) with time zone NOT NULL,
    published_at timestamp(6) with time zone NOT NULL
);

COMMIT;
