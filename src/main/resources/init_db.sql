CREATE DATABASE taxi_service
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Ukrainian_Ukraine.1251'
    LC_CTYPE = 'Ukrainian_Ukraine.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE SCHEMA public
    AUTHORIZATION postgres;

COMMENT ON SCHEMA public
    IS 'standard public schema';

GRANT ALL ON SCHEMA public TO PUBLIC;

GRANT ALL ON SCHEMA public TO postgres;

CREATE TABLE public.manufacturers
(
    id bigint NOT NULL DEFAULT nextval('manufacturers_id_seq'::regclass),
    name character varying(225) COLLATE pg_catalog."default" NOT NULL,
    country character varying(225) COLLATE pg_catalog."default" NOT NULL,
    deleted boolean DEFAULT false,
    CONSTRAINT manufacturers_pk PRIMARY KEY (id)
)


