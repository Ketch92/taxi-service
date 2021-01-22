--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1
-- Dumped by pg_dump version 13.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: taxi_service; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE taxi_service WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Ukrainian_Ukraine.1251';


ALTER DATABASE taxi_service OWNER TO postgres;

\connect taxi_service

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: cars; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cars (
    id bigint NOT NULL,
    model character varying(225) NOT NULL,
    manufacturer bigint NOT NULL,
    deleted boolean DEFAULT false
);


ALTER TABLE public.cars OWNER TO postgres;

--
-- Name: cars_drivers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cars_drivers (
    car_id bigint NOT NULL,
    driver_id bigint NOT NULL
);


ALTER TABLE public.cars_drivers OWNER TO postgres;

--
-- Name: cars_drivers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cars_drivers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: cars_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cars_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cars_id_seq OWNER TO postgres;

--
-- Name: cars_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cars_id_seq OWNED BY public.cars.id;


--
-- Name: drivers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.drivers (
    id bigint NOT NULL,
    name character varying(225) NOT NULL,
    licence_number character varying(225) NOT NULL,
    deleted boolean DEFAULT false,
    login character varying(225),
    password character varying(225)
);


ALTER TABLE public.drivers OWNER TO postgres;

--
-- Name: drivers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.drivers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.drivers_id_seq OWNER TO postgres;

--
-- Name: drivers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.drivers_id_seq OWNED BY public.drivers.id;


--
-- Name: manufacturers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.manufacturers (
    id bigint NOT NULL,
    name character varying(225) NOT NULL,
    country character varying(225) NOT NULL,
    deleted boolean DEFAULT false
);


ALTER TABLE public.manufacturers OWNER TO postgres;

--
-- Name: manufacturers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.manufacturers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.manufacturers_id_seq OWNER TO postgres;

--
-- Name: manufacturers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.manufacturers_id_seq OWNED BY public.manufacturers.id;


--
-- Name: cars id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cars ALTER COLUMN id SET DEFAULT nextval('public.cars_id_seq'::regclass);


--
-- Name: drivers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.drivers ALTER COLUMN id SET DEFAULT nextval('public.drivers_id_seq'::regclass);


--
-- Name: manufacturers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manufacturers ALTER COLUMN id SET DEFAULT nextval('public.manufacturers_id_seq'::regclass);


--
-- Data for Name: cars; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cars (id, model, manufacturer, deleted) FROM stdin;
1	Any	11	f
2	Any	2	f
3	Any	14	f
4	Any	11	f
5	Any	1	f
6	Any	6	f
7	Any	8	f
8	Any	9	f
9	Any	12	f
10	Any	2	f
\.


--
-- Data for Name: cars_drivers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cars_drivers (id, car_id, driver_id) FROM stdin;
1	1	6
2	1	3
3	1	16
4	1	7
5	1	8
6	1	5
7	1	16
8	1	16
9	2	13
10	2	17
11	3	13
12	3	17
13	3	14
14	4	1
15	4	7
16	4	12
17	4	16
18	4	5
19	4	1
20	5	14
21	5	10
22	5	3
23	5	1
24	5	4
25	5	1
26	6	9
27	6	15
28	6	3
29	6	15
30	6	3
31	6	1
32	6	15
33	6	8
34	6	4
35	7	1
36	7	7
37	7	17
38	7	6
39	7	1
40	8	1
41	8	15
42	8	9
43	8	16
44	9	7
45	9	6
46	9	14
47	9	8
48	9	4
49	9	5
50	9	11
51	10	4
52	10	11
53	10	3
54	10	3
\.


--
-- Data for Name: drivers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.drivers (id, name, licence_number, deleted, login, password) FROM stdin;
3	Name 0	number1	f	login 0	password 0
4	Name 1	number1	f	login 1	password 1
5	Name 2	number1	f	login 2	password 2
6	Name 3	number1	f	login 3	password 3
7	Name 4	number1	f	login 4	password 4
8	Name 5	number1	f	login 5	password 5
9	Name 6	number1	f	login 6	password 6
10	Name 7	number1	f	login 7	password 7
11	Name 8	number1	f	login 8	password 8
12	Name 9	number1	f	login 9	password 9
13	Name 10	number1	f	login 10	password 10
14	Name 11	number1	f	login 11	password 11
15	Name 12	number1	f	login 12	password 12
16	Name 13	number1	f	login 13	password 13
17	Name 14	number1	f	login 14	password 14
18	Kate	314638	t	Kate123	123
1	Oleh	678165	t	Oleh	strongPassword
\.


--
-- Data for Name: manufacturers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.manufacturers (id, name, country, deleted) FROM stdin;
1	Manufacturer0	Some 0	f
2	Manufacturer1	Some 1	f
3	Manufacturer2	Some 2	f
4	Manufacturer3	Some 3	f
5	Manufacturer4	Some 4	f
6	Manufacturer5	Some 5	f
7	Manufacturer6	Some 6	f
8	Manufacturer7	Some 7	f
9	Manufacturer8	Some 8	f
10	Manufacturer9	Some 9	f
11	Manufacturer10	Some 10	f
12	Manufacturer11	Some 11	f
13	Manufacturer12	Some 12	f
14	Manufacturer13	Some 13	f
15	Manufacturer14	Some 14	f
\.


--
-- Name: cars_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cars_id_seq', 10, true);


--
-- Name: drivers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.drivers_id_seq', 18, true);


--
-- Name: manufacturers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--



--
-- Name: cars cars_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cars
    ADD CONSTRAINT cars_pk PRIMARY KEY (id);


--
-- Name: drivers drivers_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.drivers
    ADD CONSTRAINT drivers_pk PRIMARY KEY (id);


--
-- Name: manufacturers manufacturers_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manufacturers
    ADD CONSTRAINT manufacturers_pk PRIMARY KEY (id);


--
-- Name: drivers_login_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX drivers_login_uindex ON public.drivers USING btree (login);


--
-- Name: cars_drivers cars_drivers__cars_cfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cars_drivers
    ADD CONSTRAINT cars_drivers__cars_cfk FOREIGN KEY (car_id) REFERENCES public.cars(id);


--
-- Name: cars_drivers cars_drivers__drivers_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cars_drivers
    ADD CONSTRAINT cars_drivers__drivers_fk FOREIGN KEY (driver_id) REFERENCES public.drivers(id);


--
-- Name: cars manufacturer; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cars
    ADD CONSTRAINT manufacturer FOREIGN KEY (manufacturer) REFERENCES public.manufacturers(id);


--
-- PostgreSQL database dump complete
--
