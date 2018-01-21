--
-- PostgreSQL database dump
--

-- Dumped from database version 10.1
-- Dumped by pg_dump version 10.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: blog_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE blog_user (
  user_name character varying(255) NOT NULL,
  email character varying(255) NOT NULL,
  enabled boolean NOT NULL,
  password character varying(255) NOT NULL,
  role character varying(255)
);


ALTER TABLE blog_user OWNER TO postgres;

--
-- Name: comment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE comment (
  id bigint NOT NULL,
  comment_text character varying(255),
  created_date bytea,
  post_id bigint,
  user_user_name character varying(255)
);


ALTER TABLE comment OWNER TO postgres;

--
-- Name: comment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE comment_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE comment_id_seq OWNER TO postgres;

--
-- Name: comment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE comment_id_seq OWNED BY comment.id;


--
-- Name: persistent_logions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE persistent_logions (
  username character varying(65) NOT NULL,
  series character varying(65) NOT NULL,
  token character varying(65) NOT NULL,
  last_used timestamp without time zone NOT NULL,
  updated_at timestamp without time zone DEFAULT '2018-01-21 21:03:12.523964'::timestamp without time zone
);


ALTER TABLE persistent_logions OWNER TO postgres;

--
-- Name: post; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE post (
  id bigint NOT NULL,
  created_date bytea,
  post_text character varying(255),
  user_user_name character varying(255)
);


ALTER TABLE post OWNER TO postgres;

--
-- Name: post_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE post_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE post_id_seq OWNER TO postgres;

--
-- Name: post_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE post_id_seq OWNED BY post.id;


--
-- Name: comment id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comment ALTER COLUMN id SET DEFAULT nextval('comment_id_seq'::regclass);


--
-- Name: post id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY post ALTER COLUMN id SET DEFAULT nextval('post_id_seq'::regclass);


--
-- Data for Name: blog_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY blog_user (user_name, email, enabled, password, role) FROM stdin;
carlos	carlos@gmail.com	t	$2a$10$iaSQ3pq3lulvp.HDIid5BOqeq2t5DywvnxeZwnRtoXoLdOshRj2sS	USER
sailor	sailor@gmail.com	t	$2a$10$vPPWBClcpSYAYNC0XW2WSu2euNYzwYw78b.Iuzr.qSfNWyMSCrIni	USER
\.


--
-- Data for Name: comment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY comment (id, comment_text, created_date, post_id, user_user_name) FROM stdin;
1	you are an idiot!	\\xaced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770e05000007e201121405100121eac078	1	sailor
2	You should sail to Antarctica!	\\xaced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770e05000007e2011214051002bde78078	1	sailor
3	Shut up!	\\xaced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770e05000007e201121405100319750078	2	sailor
4	You need to go and eat spinach! As much as you can!	\\xaced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770e05000007e201121405100375028078	3	sailor
\.


--
-- Data for Name: persistent_logions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY persistent_logions (username, series, token, last_used, updated_at) FROM stdin;
\.


--
-- Data for Name: post; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY post (id, created_date, post_text, user_user_name) FROM stdin;
1	\\xaced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770e05000007e2011214050f32021fc078	First post !!! UUUUUhuuuuu!	carlos
2	\\xaced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770e05000007e2011214050f33ad5ec078	I like this blog posting so much :)	carlos
3	\\xaced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770e05000007e2011214050f33f9aa0078	To be or not to be? What is the question.	carlos
11	\\xaced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770e05000007e201130c140617e6c64078	new post	carlos
12	\\xaced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770e05000007e201130c16252f25b3c078	the best post from UI page	carlos
13	\\xaced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770e05000007e201130c300b3ae3af0078	one more UPDATE	carlos
15	\\xaced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770e05000007e201130f19060243d58078	hello monkey	carlos
\.


--
-- Name: comment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('comment_id_seq', 4, true);


--
-- Name: post_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('post_id_seq', 15, true);


--
-- Name: blog_user blog_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY blog_user
  ADD CONSTRAINT blog_user_pkey PRIMARY KEY (user_name);


--
-- Name: comment comment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comment
  ADD CONSTRAINT comment_pkey PRIMARY KEY (id);


--
-- Name: persistent_logions persistent_logions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY persistent_logions
  ADD CONSTRAINT persistent_logions_pkey PRIMARY KEY (series);


--
-- Name: persistent_logions persistent_logions_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY persistent_logions
  ADD CONSTRAINT persistent_logions_username_key UNIQUE (username);


--
-- Name: post post_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY post
  ADD CONSTRAINT post_pkey PRIMARY KEY (id);


--
-- Name: post fk3gdaofv3mogy4tunnef7dk6nx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY post
  ADD CONSTRAINT fk3gdaofv3mogy4tunnef7dk6nx FOREIGN KEY (user_user_name) REFERENCES blog_user(user_name);


--
-- Name: comment fkqjxot6qlsqsiscb36gvq19a6h; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comment
  ADD CONSTRAINT fkqjxot6qlsqsiscb36gvq19a6h FOREIGN KEY (user_user_name) REFERENCES blog_user(user_name);


--
-- Name: comment fks1slvnkuemjsq2kj4h3vhx7i1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comment
  ADD CONSTRAINT fks1slvnkuemjsq2kj4h3vhx7i1 FOREIGN KEY (post_id) REFERENCES post(id);


--
-- PostgreSQL database dump complete
--

