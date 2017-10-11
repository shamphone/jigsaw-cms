--
-- PostgreSQL database cluster dump
--

\connect "template1"

--
-- Users
--

DELETE FROM pg_shadow WHERE usesysid <> (SELECT datdba FROM pg_database WHERE datname = 'template0');

CREATE USER lyvcuser WITH SYSID 102 PASSWORD 'md595196cd2eeed826fc895b969d4f9891b' CREATEDB NOCREATEUSER VALID UNTIL 'infinity';
CREATE USER lyvccontact WITH SYSID 103 PASSWORD 'md5b87c8835218b8abdb4ac330de4cd06a7' NOCREATEDB NOCREATEUSER VALID UNTIL 'infinity';
CREATE USER lyvcpendingmessage WITH SYSID 104 PASSWORD 'md50020001690ca09604d9d8cdd5386e59c' CREATEDB NOCREATEUSER VALID UNTIL 'infinity';


--
-- Groups
--

DELETE FROM pg_group;


--
-- Database creation
--

DROP DATABASE conference;
CREATE DATABASE conference WITH TEMPLATE = template0 OWNER = postgres ENCODING = 'UNICODE';
DROP DATABASE lyvccontact;
CREATE DATABASE lyvccontact WITH TEMPLATE = template0 OWNER = lyvccontact ENCODING = 'UNICODE';
DROP DATABASE lyvcpendingmessage;
CREATE DATABASE lyvcpendingmessage WITH TEMPLATE = template0 OWNER = lyvcpendingmessage ENCODING = 'UNICODE';
DROP DATABASE lyvcuser;
CREATE DATABASE lyvcuser WITH TEMPLATE = template0 OWNER = lyvcuser ENCODING = 'UNICODE';
DROP DATABASE servermonitor;
CREATE DATABASE servermonitor WITH TEMPLATE = template0 OWNER = postgres ENCODING = 'UNICODE';


--
-- Users
--

ALTER USER postgres WITH PASSWORD 'md53175bce1d3201d16594cebf9d7eb3f9d' CREATEDB CREATEUSER;


\connect conference

--
-- PostgreSQL database dump
--

SET client_encoding = 'UNICODE';
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'Standard public schema';


SET search_path = public, pg_catalog;

--
-- Name: plpgsql_call_handler(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plpgsql_call_handler() RETURNS language_handler
    AS '$libdir/plpgsql', 'plpgsql_call_handler'
    LANGUAGE c;


ALTER FUNCTION public.plpgsql_call_handler() OWNER TO postgres;

--
-- Name: plpgsql_validator(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plpgsql_validator(oid) RETURNS void
    AS '$libdir/plpgsql', 'plpgsql_validator'
    LANGUAGE c;


ALTER FUNCTION public.plpgsql_validator(oid) OWNER TO postgres;

--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: public; Owner: 
--

CREATE TRUSTED PROCEDURAL LANGUAGE plpgsql HANDLER plpgsql_call_handler VALIDATOR plpgsql_validator;


--
-- Name: database_size(name); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION database_size(name) RETURNS bigint
    AS '$libdir/dbsize', 'database_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.database_size(name) OWNER TO postgres;

--
-- Name: pg_database_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_database_size(oid) RETURNS bigint
    AS '$libdir/dbsize', 'pg_database_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_database_size(oid) OWNER TO postgres;

--
-- Name: pg_dir_ls(text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_dir_ls(text, boolean) RETURNS SETOF text
    AS '$libdir/admin', 'pg_dir_ls'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_dir_ls(text, boolean) OWNER TO postgres;

--
-- Name: pg_file_length(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_length(text) RETURNS bigint
    AS $_$SELECT len FROM pg_file_stat($1) AS s(len int8, c timestamp, a timestamp, m timestamp, i bool)$_$
    LANGUAGE sql STRICT;


ALTER FUNCTION public.pg_file_length(text) OWNER TO postgres;

--
-- Name: pg_file_read(text, bigint, bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_read(text, bigint, bigint) RETURNS text
    AS '$libdir/admin', 'pg_file_read'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_read(text, bigint, bigint) OWNER TO postgres;

--
-- Name: pg_file_rename(text, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_rename(text, text, text) RETURNS boolean
    AS '$libdir/admin', 'pg_file_rename'
    LANGUAGE c;


ALTER FUNCTION public.pg_file_rename(text, text, text) OWNER TO postgres;

--
-- Name: pg_file_rename(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_rename(text, text) RETURNS boolean
    AS $_$SELECT pg_file_rename($1, $2, NULL); $_$
    LANGUAGE sql STRICT;


ALTER FUNCTION public.pg_file_rename(text, text) OWNER TO postgres;

--
-- Name: pg_file_stat(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_stat(text) RETURNS record
    AS '$libdir/admin', 'pg_file_stat'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_stat(text) OWNER TO postgres;

--
-- Name: pg_file_unlink(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_unlink(text) RETURNS boolean
    AS '$libdir/admin', 'pg_file_unlink'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_unlink(text) OWNER TO postgres;

--
-- Name: pg_file_write(text, text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_write(text, text, boolean) RETURNS bigint
    AS '$libdir/admin', 'pg_file_write'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_write(text, text, boolean) OWNER TO postgres;

--
-- Name: pg_logdir_ls(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_logdir_ls() RETURNS SETOF record
    AS '$libdir/admin', 'pg_logdir_ls'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_logdir_ls() OWNER TO postgres;

--
-- Name: pg_postmaster_starttime(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_postmaster_starttime() RETURNS timestamp without time zone
    AS '$libdir/admin', 'pg_postmaster_starttime'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_postmaster_starttime() OWNER TO postgres;

--
-- Name: pg_relation_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_relation_size(oid) RETURNS bigint
    AS '$libdir/dbsize', 'pg_relation_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_relation_size(oid) OWNER TO postgres;

--
-- Name: pg_reload_conf(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_reload_conf() RETURNS integer
    AS '$libdir/admin', 'pg_reload_conf'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.pg_reload_conf() OWNER TO postgres;

--
-- Name: pg_size_pretty(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_size_pretty(bigint) RETURNS text
    AS '$libdir/dbsize', 'pg_size_pretty'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_size_pretty(bigint) OWNER TO postgres;

--
-- Name: pg_tablespace_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_tablespace_size(oid) RETURNS bigint
    AS '$libdir/dbsize', 'pg_tablespace_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_tablespace_size(oid) OWNER TO postgres;

--
-- Name: relation_size(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION relation_size(text) RETURNS bigint
    AS '$libdir/dbsize', 'relation_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.relation_size(text) OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = true;

--
-- Name: conference; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE conference (
    id bigint NOT NULL,
    title character varying(255),
    description character varying(255),
    conferencemodelid bigint,
    starttime timestamp without time zone,
    endtime timestamp without time zone,
    conference_creator bigint DEFAULT 0 NOT NULL
);


ALTER TABLE public.conference OWNER TO postgres;

--
-- Name: conferencedoc; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE conferencedoc (
    conferencedoc_id bigint NOT NULL,
    conferencedoc_name character varying(255),
    conferencedoc_url character varying(255),
    conference_id bigint,
    conferencedoc_filename character varying(255) NOT NULL
);


ALTER TABLE public.conferencedoc OWNER TO postgres;

SET default_with_oids = false;

--
-- Name: conferencemember; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE conferencemember (
    modelroleid bigint NOT NULL,
    userid bigint NOT NULL,
    id bigint NOT NULL,
    conferenceid bigint NOT NULL
);


ALTER TABLE public.conferencemember OWNER TO postgres;

SET default_with_oids = true;

--
-- Name: conferencemodel; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE conferencemodel (
    id bigint NOT NULL,
    name character varying(355),
    description character varying(355),
    ispredefined boolean DEFAULT false
);


ALTER TABLE public.conferencemodel OWNER TO postgres;

--
-- Name: conferencemodelrole; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE conferencemodelrole (
    id bigint NOT NULL,
    name character varying(255),
    description character varying(255),
    isdefault boolean,
    modelid bigint NOT NULL
);


ALTER TABLE public.conferencemodelrole OWNER TO postgres;

SET default_with_oids = false;

--
-- Name: conferenceright; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE conferenceright (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(200)
);


ALTER TABLE public.conferenceright OWNER TO postgres;

--
-- Name: conferenceroleright; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE conferenceroleright (
    roleid bigint NOT NULL,
    rightid integer NOT NULL
);


ALTER TABLE public.conferenceroleright OWNER TO postgres;

--
-- Name: notifyregister; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE notifyregister (
    id bigint NOT NULL,
    ip character varying(255),
    port integer
);


ALTER TABLE public.notifyregister OWNER TO postgres;

--
-- Name: pg_logdir_ls; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW pg_logdir_ls AS
    SELECT a.filetime, a.filename FROM pg_logdir_ls() a(filetime timestamp without time zone, filename text);


ALTER TABLE public.pg_logdir_ls OWNER TO postgres;

--
-- Name: primarykey_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE primarykey_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.primarykey_sequence OWNER TO postgres;

--
-- Name: primarykey_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('primarykey_sequence', 18565, true);


SET default_with_oids = true;

--
-- Name: systemrole; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE systemrole (
    role_id bigint NOT NULL,
    role_name character varying(255),
    role_desc character varying(255),
    role_users character varying(255)
);


ALTER TABLE public.systemrole OWNER TO postgres;

--
-- Data for Name: conference; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY conference (id, title, description, conferencemodelid, starttime, endtime, conference_creator) FROM stdin;
\.


--
-- Data for Name: conferencedoc; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY conferencedoc (conferencedoc_id, conferencedoc_name, conferencedoc_url, conference_id, conferencedoc_filename) FROM stdin;
\.


--
-- Data for Name: conferencemember; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY conferencemember (modelroleid, userid, id, conferenceid) FROM stdin;
\.


--
-- Data for Name: conferencemodel; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY conferencemodel (id, name, description, ispredefined) FROM stdin;
1	常规会议模式	设立一个会议主持人，其余均为参会人员；主持人有权限制参会人员发言。参会人员可以随意调看其他参会的视频，但音频由主持人控制，主持人可以随时说话，参会人员需要经过主持人许可之后才可以说话	t
2	网上培训模式	参会人员的角色为讲师、助教和学员	t
3	网上咨询模式	参会人员的角色为咨询师和咨询人	t
4	临时会议模式	会议成员包括发起人和参会人	t
5	主控会议模式	设立一个主控角色，其余角色均为受控人员	t
\.


--
-- Data for Name: conferencemodelrole; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY conferencemodelrole (id, name, description, isdefault, modelid) FROM stdin;
5	主持人	会议主持人	f	1
6	参会人员	会议参会人员	t	1
7	讲师	讲师	f	2
8	助教	助教	f	2
9	学员	学员	t	2
10	咨询师	咨询师	f	3
11	咨询人	咨询人	t	3
12	发起人	会议发起人	f	4
13	参会人	参会人员	t	4
15	受控人	接受主控人的控制	t	5
14	主控人	具有集中控制的权限	f	5
\.


--
-- Data for Name: conferenceright; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY conferenceright (id, name, description) FROM stdin;
1	视频发送	视频发送
2	视频接收	视频接收
3	音频发送	音频发送
4	音频接收	音频接收
5	发言权申请	发言权申请
6	发言权控制	发言权控制
7	桌面共享发送	桌面共享发送
8	桌面共享接收	桌面共享接收
9	文件发送	文件发送
10	文件接收	文件接收
11	成员邀请	成员邀请
12	成员驱逐	成员驱逐
13	集中控制	可以控制其它参会人员视频观看和音频收听
14	接受集中控制	被具有集中控制权限的人控制
\.


--
-- Data for Name: conferenceroleright; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY conferenceroleright (roleid, rightid) FROM stdin;
5	1
5	2
5	3
5	4
5	7
5	8
5	9
5	10
5	11
5	12
6	1
6	2
6	7
6	8
6	9
6	10
7	1
7	2
7	3
7	4
7	6
7	7
7	8
7	9
7	10
7	11
7	12
8	1
8	2
8	3
8	4
8	6
8	7
8	8
8	9
8	10
8	11
8	12
9	2
9	4
9	5
9	8
9	10
10	1
10	2
10	3
10	4
10	7
10	8
10	9
10	10
10	11
11	1
11	2
11	3
11	4
11	7
11	8
11	9
11	10
11	11
12	3
12	4
12	6
12	7
12	8
12	9
12	10
12	11
12	12
13	3
13	4
13	7
13	8
13	9
13	10
13	11
14	1
14	2
14	3
14	4
14	5
14	6
14	7
14	8
14	9
14	10
14	11
14	12
14	13
15	1
15	2
15	3
15	4
15	14
6	4
6	3
12	1
12	2
13	1
13	2
\.


--
-- Data for Name: notifyregister; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY notifyregister (id, ip, port) FROM stdin;
\.


--
-- Data for Name: systemrole; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY systemrole (role_id, role_name, role_desc, role_users) FROM stdin;
1	 系统管理员	系统管理员	2
\.


--
-- Name: conference_doc_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY conferencedoc
    ADD CONSTRAINT conference_doc_pkey PRIMARY KEY (conferencedoc_id);


ALTER INDEX public.conference_doc_pkey OWNER TO postgres;

--
-- Name: conference_model_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY conferencemodel
    ADD CONSTRAINT conference_model_pkey PRIMARY KEY (id);


ALTER INDEX public.conference_model_pkey OWNER TO postgres;

--
-- Name: conference_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY conference
    ADD CONSTRAINT conference_pkey PRIMARY KEY (id);


ALTER INDEX public.conference_pkey OWNER TO postgres;

--
-- Name: conferencemember_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY conferencemember
    ADD CONSTRAINT conferencemember_pkey PRIMARY KEY (id);


ALTER INDEX public.conferencemember_pkey OWNER TO postgres;

--
-- Name: notifyregister_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY notifyregister
    ADD CONSTRAINT notifyregister_pkey PRIMARY KEY (id);


ALTER INDEX public.notifyregister_pkey OWNER TO postgres;

--
-- Name: pkid; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY conferenceright
    ADD CONSTRAINT pkid PRIMARY KEY (id);


ALTER INDEX public.pkid OWNER TO postgres;

--
-- Name: role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY conferencemodelrole
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


ALTER INDEX public.role_pkey OWNER TO postgres;

--
-- Name: roleright_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY conferenceroleright
    ADD CONSTRAINT roleright_pkey PRIMARY KEY (roleid, rightid);


ALTER INDEX public.roleright_pkey OWNER TO postgres;

--
-- Name: systemrole_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY systemrole
    ADD CONSTRAINT systemrole_pkey PRIMARY KEY (role_id);


ALTER INDEX public.systemrole_pkey OWNER TO postgres;

--
-- Name: conferenceid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY conferencemember
    ADD CONSTRAINT conferenceid_fkey FOREIGN KEY (conferenceid) REFERENCES conference(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: fk2b5f451c49c4f72d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY conference
    ADD CONSTRAINT fk2b5f451c49c4f72d FOREIGN KEY (conferencemodelid) REFERENCES conferencemodel(id);


--
-- Name: fk31ec0b95fd6b571e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY conferencedoc
    ADD CONSTRAINT fk31ec0b95fd6b571e FOREIGN KEY (conference_id) REFERENCES conference(id);


--
-- Name: modelid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY conferencemember
    ADD CONSTRAINT modelid FOREIGN KEY (modelroleid) REFERENCES conferencemodelrole(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: roleid_pkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY conferenceroleright
    ADD CONSTRAINT roleid_pkey FOREIGN KEY (roleid) REFERENCES conferencemodelrole(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

\connect lyvccontact

--
-- PostgreSQL database dump
--

SET client_encoding = 'UNICODE';
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'Standard public schema';


SET search_path = public, pg_catalog;

--
-- Name: plpgsql_call_handler(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plpgsql_call_handler() RETURNS language_handler
    AS '$libdir/plpgsql', 'plpgsql_call_handler'
    LANGUAGE c;


ALTER FUNCTION public.plpgsql_call_handler() OWNER TO postgres;

--
-- Name: plpgsql_validator(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plpgsql_validator(oid) RETURNS void
    AS '$libdir/plpgsql', 'plpgsql_validator'
    LANGUAGE c;


ALTER FUNCTION public.plpgsql_validator(oid) OWNER TO postgres;

--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: public; Owner: 
--

CREATE TRUSTED PROCEDURAL LANGUAGE plpgsql HANDLER plpgsql_call_handler VALIDATOR plpgsql_validator;


--
-- Name: database_size(name); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION database_size(name) RETURNS bigint
    AS '$libdir/dbsize', 'database_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.database_size(name) OWNER TO postgres;

--
-- Name: pg_database_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_database_size(oid) RETURNS bigint
    AS '$libdir/dbsize', 'pg_database_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_database_size(oid) OWNER TO postgres;

--
-- Name: pg_dir_ls(text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_dir_ls(text, boolean) RETURNS SETOF text
    AS '$libdir/admin', 'pg_dir_ls'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_dir_ls(text, boolean) OWNER TO postgres;

--
-- Name: pg_file_length(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_length(text) RETURNS bigint
    AS $_$SELECT len FROM pg_file_stat($1) AS s(len int8, c timestamp, a timestamp, m timestamp, i bool)$_$
    LANGUAGE sql STRICT;


ALTER FUNCTION public.pg_file_length(text) OWNER TO postgres;

--
-- Name: pg_file_read(text, bigint, bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_read(text, bigint, bigint) RETURNS text
    AS '$libdir/admin', 'pg_file_read'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_read(text, bigint, bigint) OWNER TO postgres;

--
-- Name: pg_file_rename(text, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_rename(text, text, text) RETURNS boolean
    AS '$libdir/admin', 'pg_file_rename'
    LANGUAGE c;


ALTER FUNCTION public.pg_file_rename(text, text, text) OWNER TO postgres;

--
-- Name: pg_file_rename(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_rename(text, text) RETURNS boolean
    AS $_$SELECT pg_file_rename($1, $2, NULL); $_$
    LANGUAGE sql STRICT;


ALTER FUNCTION public.pg_file_rename(text, text) OWNER TO postgres;

--
-- Name: pg_file_stat(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_stat(text) RETURNS record
    AS '$libdir/admin', 'pg_file_stat'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_stat(text) OWNER TO postgres;

--
-- Name: pg_file_unlink(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_unlink(text) RETURNS boolean
    AS '$libdir/admin', 'pg_file_unlink'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_unlink(text) OWNER TO postgres;

--
-- Name: pg_file_write(text, text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_write(text, text, boolean) RETURNS bigint
    AS '$libdir/admin', 'pg_file_write'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_write(text, text, boolean) OWNER TO postgres;

--
-- Name: pg_logdir_ls(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_logdir_ls() RETURNS SETOF record
    AS '$libdir/admin', 'pg_logdir_ls'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_logdir_ls() OWNER TO postgres;

--
-- Name: pg_postmaster_starttime(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_postmaster_starttime() RETURNS timestamp without time zone
    AS '$libdir/admin', 'pg_postmaster_starttime'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_postmaster_starttime() OWNER TO postgres;

--
-- Name: pg_relation_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_relation_size(oid) RETURNS bigint
    AS '$libdir/dbsize', 'pg_relation_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_relation_size(oid) OWNER TO postgres;

--
-- Name: pg_reload_conf(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_reload_conf() RETURNS integer
    AS '$libdir/admin', 'pg_reload_conf'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.pg_reload_conf() OWNER TO postgres;

--
-- Name: pg_size_pretty(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_size_pretty(bigint) RETURNS text
    AS '$libdir/dbsize', 'pg_size_pretty'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_size_pretty(bigint) OWNER TO postgres;

--
-- Name: pg_tablespace_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_tablespace_size(oid) RETURNS bigint
    AS '$libdir/dbsize', 'pg_tablespace_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_tablespace_size(oid) OWNER TO postgres;

--
-- Name: relation_size(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION relation_size(text) RETURNS bigint
    AS '$libdir/dbsize', 'relation_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.relation_size(text) OWNER TO postgres;

--
-- Name: groupid; Type: SEQUENCE; Schema: public; Owner: lyvccontact
--

CREATE SEQUENCE groupid
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.groupid OWNER TO lyvccontact;

--
-- Name: groupid; Type: SEQUENCE SET; Schema: public; Owner: lyvccontact
--

SELECT pg_catalog.setval('groupid', 8199, true);


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: groups; Type: TABLE; Schema: public; Owner: lyvccontact; Tablespace: 
--

CREATE TABLE groups (
    id bigint NOT NULL,
    name character varying(200) NOT NULL,
    userid bigint NOT NULL,
    displaysort integer DEFAULT 0
);


ALTER TABLE public.groups OWNER TO lyvccontact;

--
-- Name: groupuser; Type: TABLE; Schema: public; Owner: lyvccontact; Tablespace: 
--

CREATE TABLE groupuser (
    groupid bigint NOT NULL,
    userid bigint NOT NULL
);


ALTER TABLE public.groupuser OWNER TO lyvccontact;

--
-- Name: pg_logdir_ls; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW pg_logdir_ls AS
    SELECT a.filetime, a.filename FROM pg_logdir_ls() a(filetime timestamp without time zone, filename text);


ALTER TABLE public.pg_logdir_ls OWNER TO postgres;

--
-- Name: userdefaultgroup; Type: TABLE; Schema: public; Owner: lyvccontact; Tablespace: 
--

CREATE TABLE userdefaultgroup (
    userid bigint NOT NULL,
    groupid bigint NOT NULL
);


ALTER TABLE public.userdefaultgroup OWNER TO lyvccontact;

--
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: lyvccontact
--

COPY groups (id, name, userid, displaysort) FROM stdin;
\.


--
-- Data for Name: groupuser; Type: TABLE DATA; Schema: public; Owner: lyvccontact
--

COPY groupuser (groupid, userid) FROM stdin;
\.


--
-- Data for Name: userdefaultgroup; Type: TABLE DATA; Schema: public; Owner: lyvccontact
--

COPY userdefaultgroup (userid, groupid) FROM stdin;
\.


--
-- Name: GroupPK; Type: CONSTRAINT; Schema: public; Owner: lyvccontact; Tablespace: 
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT "GroupPK" PRIMARY KEY (id);


ALTER INDEX public."GroupPK" OWNER TO lyvccontact;

--
-- Name: GroupUserPK; Type: CONSTRAINT; Schema: public; Owner: lyvccontact; Tablespace: 
--

ALTER TABLE ONLY groupuser
    ADD CONSTRAINT "GroupUserPK" PRIMARY KEY (groupid, userid);


ALTER INDEX public."GroupUserPK" OWNER TO lyvccontact;

--
-- Name: UserDefaultGroupPK; Type: CONSTRAINT; Schema: public; Owner: lyvccontact; Tablespace: 
--

ALTER TABLE ONLY userdefaultgroup
    ADD CONSTRAINT "UserDefaultGroupPK" PRIMARY KEY (userid);


ALTER INDEX public."UserDefaultGroupPK" OWNER TO lyvccontact;

--
-- Name: GroupUserIndex; Type: INDEX; Schema: public; Owner: lyvccontact; Tablespace: 
--

CREATE INDEX "GroupUserIndex" ON groupuser USING btree (groupid);


ALTER INDEX public."GroupUserIndex" OWNER TO lyvccontact;

--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--


\connect lyvcpendingmessage

--
-- PostgreSQL database dump
--

SET client_encoding = 'UNICODE';
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'Standard public schema';


SET search_path = public, pg_catalog;

--
-- Name: plpgsql_call_handler(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plpgsql_call_handler() RETURNS language_handler
    AS '$libdir/plpgsql', 'plpgsql_call_handler'
    LANGUAGE c;


ALTER FUNCTION public.plpgsql_call_handler() OWNER TO postgres;

--
-- Name: plpgsql_validator(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plpgsql_validator(oid) RETURNS void
    AS '$libdir/plpgsql', 'plpgsql_validator'
    LANGUAGE c;


ALTER FUNCTION public.plpgsql_validator(oid) OWNER TO postgres;

--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: public; Owner: 
--

CREATE TRUSTED PROCEDURAL LANGUAGE plpgsql HANDLER plpgsql_call_handler VALIDATOR plpgsql_validator;


--
-- Name: database_size(name); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION database_size(name) RETURNS bigint
    AS '$libdir/dbsize', 'database_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.database_size(name) OWNER TO postgres;

--
-- Name: pg_database_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_database_size(oid) RETURNS bigint
    AS '$libdir/dbsize', 'pg_database_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_database_size(oid) OWNER TO postgres;

--
-- Name: pg_dir_ls(text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_dir_ls(text, boolean) RETURNS SETOF text
    AS '$libdir/admin', 'pg_dir_ls'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_dir_ls(text, boolean) OWNER TO postgres;

--
-- Name: pg_file_length(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_length(text) RETURNS bigint
    AS $_$SELECT len FROM pg_file_stat($1) AS s(len int8, c timestamp, a timestamp, m timestamp, i bool)$_$
    LANGUAGE sql STRICT;


ALTER FUNCTION public.pg_file_length(text) OWNER TO postgres;

--
-- Name: pg_file_read(text, bigint, bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_read(text, bigint, bigint) RETURNS text
    AS '$libdir/admin', 'pg_file_read'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_read(text, bigint, bigint) OWNER TO postgres;

--
-- Name: pg_file_rename(text, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_rename(text, text, text) RETURNS boolean
    AS '$libdir/admin', 'pg_file_rename'
    LANGUAGE c;


ALTER FUNCTION public.pg_file_rename(text, text, text) OWNER TO postgres;

--
-- Name: pg_file_rename(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_rename(text, text) RETURNS boolean
    AS $_$SELECT pg_file_rename($1, $2, NULL); $_$
    LANGUAGE sql STRICT;


ALTER FUNCTION public.pg_file_rename(text, text) OWNER TO postgres;

--
-- Name: pg_file_stat(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_stat(text) RETURNS record
    AS '$libdir/admin', 'pg_file_stat'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_stat(text) OWNER TO postgres;

--
-- Name: pg_file_unlink(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_unlink(text) RETURNS boolean
    AS '$libdir/admin', 'pg_file_unlink'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_unlink(text) OWNER TO postgres;

--
-- Name: pg_file_write(text, text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_write(text, text, boolean) RETURNS bigint
    AS '$libdir/admin', 'pg_file_write'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_write(text, text, boolean) OWNER TO postgres;

--
-- Name: pg_logdir_ls(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_logdir_ls() RETURNS SETOF record
    AS '$libdir/admin', 'pg_logdir_ls'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_logdir_ls() OWNER TO postgres;

--
-- Name: pg_postmaster_starttime(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_postmaster_starttime() RETURNS timestamp without time zone
    AS '$libdir/admin', 'pg_postmaster_starttime'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_postmaster_starttime() OWNER TO postgres;

--
-- Name: pg_relation_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_relation_size(oid) RETURNS bigint
    AS '$libdir/dbsize', 'pg_relation_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_relation_size(oid) OWNER TO postgres;

--
-- Name: pg_reload_conf(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_reload_conf() RETURNS integer
    AS '$libdir/admin', 'pg_reload_conf'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.pg_reload_conf() OWNER TO postgres;

--
-- Name: pg_size_pretty(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_size_pretty(bigint) RETURNS text
    AS '$libdir/dbsize', 'pg_size_pretty'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_size_pretty(bigint) OWNER TO postgres;

--
-- Name: pg_tablespace_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_tablespace_size(oid) RETURNS bigint
    AS '$libdir/dbsize', 'pg_tablespace_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_tablespace_size(oid) OWNER TO postgres;

--
-- Name: relation_size(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION relation_size(text) RETURNS bigint
    AS '$libdir/dbsize', 'relation_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.relation_size(text) OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: pendingmessage; Type: TABLE; Schema: public; Owner: lyvcpendingmessage; Tablespace: 
--

CREATE TABLE pendingmessage (
    id bigint NOT NULL,
    receiverid bigint NOT NULL,
    message text NOT NULL,
    savedate timestamp without time zone NOT NULL
);


ALTER TABLE public.pendingmessage OWNER TO lyvcpendingmessage;

--
-- Name: pendingmessageid; Type: SEQUENCE; Schema: public; Owner: lyvcpendingmessage
--

CREATE SEQUENCE pendingmessageid
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.pendingmessageid OWNER TO lyvcpendingmessage;

--
-- Name: pendingmessageid; Type: SEQUENCE SET; Schema: public; Owner: lyvcpendingmessage
--

SELECT pg_catalog.setval('pendingmessageid', 2417, true);


--
-- Name: pg_logdir_ls; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW pg_logdir_ls AS
    SELECT a.filetime, a.filename FROM pg_logdir_ls() a(filetime timestamp without time zone, filename text);


ALTER TABLE public.pg_logdir_ls OWNER TO postgres;

--
-- Data for Name: pendingmessage; Type: TABLE DATA; Schema: public; Owner: lyvcpendingmessage
--

COPY pendingmessage (id, receiverid, message, savedate) FROM stdin;
\.


--
-- Name: pendingmessage_pk; Type: CONSTRAINT; Schema: public; Owner: lyvcpendingmessage; Tablespace: 
--

ALTER TABLE ONLY pendingmessage
    ADD CONSTRAINT pendingmessage_pk PRIMARY KEY (id);


ALTER INDEX public.pendingmessage_pk OWNER TO lyvcpendingmessage;

--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

\connect lyvcuser

--
-- PostgreSQL database dump
--

SET client_encoding = 'UNICODE';
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'Standard public schema';


SET search_path = public, pg_catalog;

--
-- Name: plpgsql_call_handler(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plpgsql_call_handler() RETURNS language_handler
    AS '$libdir/plpgsql', 'plpgsql_call_handler'
    LANGUAGE c;


ALTER FUNCTION public.plpgsql_call_handler() OWNER TO postgres;

--
-- Name: plpgsql_validator(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plpgsql_validator(oid) RETURNS void
    AS '$libdir/plpgsql', 'plpgsql_validator'
    LANGUAGE c;


ALTER FUNCTION public.plpgsql_validator(oid) OWNER TO postgres;

--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: public; Owner: 
--

CREATE TRUSTED PROCEDURAL LANGUAGE plpgsql HANDLER plpgsql_call_handler VALIDATOR plpgsql_validator;


--
-- Name: database_size(name); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION database_size(name) RETURNS bigint
    AS '$libdir/dbsize', 'database_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.database_size(name) OWNER TO postgres;

--
-- Name: pg_database_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_database_size(oid) RETURNS bigint
    AS '$libdir/dbsize', 'pg_database_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_database_size(oid) OWNER TO postgres;

--
-- Name: pg_dir_ls(text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_dir_ls(text, boolean) RETURNS SETOF text
    AS '$libdir/admin', 'pg_dir_ls'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_dir_ls(text, boolean) OWNER TO postgres;

--
-- Name: pg_file_length(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_length(text) RETURNS bigint
    AS $_$SELECT len FROM pg_file_stat($1) AS s(len int8, c timestamp, a timestamp, m timestamp, i bool)$_$
    LANGUAGE sql STRICT;


ALTER FUNCTION public.pg_file_length(text) OWNER TO postgres;

--
-- Name: pg_file_read(text, bigint, bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_read(text, bigint, bigint) RETURNS text
    AS '$libdir/admin', 'pg_file_read'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_read(text, bigint, bigint) OWNER TO postgres;

--
-- Name: pg_file_rename(text, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_rename(text, text, text) RETURNS boolean
    AS '$libdir/admin', 'pg_file_rename'
    LANGUAGE c;


ALTER FUNCTION public.pg_file_rename(text, text, text) OWNER TO postgres;

--
-- Name: pg_file_rename(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_rename(text, text) RETURNS boolean
    AS $_$SELECT pg_file_rename($1, $2, NULL); $_$
    LANGUAGE sql STRICT;


ALTER FUNCTION public.pg_file_rename(text, text) OWNER TO postgres;

--
-- Name: pg_file_stat(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_stat(text) RETURNS record
    AS '$libdir/admin', 'pg_file_stat'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_stat(text) OWNER TO postgres;

--
-- Name: pg_file_unlink(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_unlink(text) RETURNS boolean
    AS '$libdir/admin', 'pg_file_unlink'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_unlink(text) OWNER TO postgres;

--
-- Name: pg_file_write(text, text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_write(text, text, boolean) RETURNS bigint
    AS '$libdir/admin', 'pg_file_write'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_write(text, text, boolean) OWNER TO postgres;

--
-- Name: pg_logdir_ls(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_logdir_ls() RETURNS SETOF record
    AS '$libdir/admin', 'pg_logdir_ls'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_logdir_ls() OWNER TO postgres;

--
-- Name: pg_postmaster_starttime(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_postmaster_starttime() RETURNS timestamp without time zone
    AS '$libdir/admin', 'pg_postmaster_starttime'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_postmaster_starttime() OWNER TO postgres;

--
-- Name: pg_relation_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_relation_size(oid) RETURNS bigint
    AS '$libdir/dbsize', 'pg_relation_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_relation_size(oid) OWNER TO postgres;

--
-- Name: pg_reload_conf(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_reload_conf() RETURNS integer
    AS '$libdir/admin', 'pg_reload_conf'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.pg_reload_conf() OWNER TO postgres;

--
-- Name: pg_size_pretty(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_size_pretty(bigint) RETURNS text
    AS '$libdir/dbsize', 'pg_size_pretty'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_size_pretty(bigint) OWNER TO postgres;

--
-- Name: pg_tablespace_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_tablespace_size(oid) RETURNS bigint
    AS '$libdir/dbsize', 'pg_tablespace_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_tablespace_size(oid) OWNER TO postgres;

--
-- Name: relation_size(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION relation_size(text) RETURNS bigint
    AS '$libdir/dbsize', 'relation_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.relation_size(text) OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: groupmember; Type: TABLE; Schema: public; Owner: lyvcuser; Tablespace: 
--

CREATE TABLE groupmember (
    groupid bigint NOT NULL,
    memberid bigint NOT NULL
);


ALTER TABLE public.groupmember OWNER TO lyvcuser;

--
-- Name: pg_logdir_ls; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW pg_logdir_ls AS
    SELECT a.filetime, a.filename FROM pg_logdir_ls() a(filetime timestamp without time zone, filename text);


ALTER TABLE public.pg_logdir_ls OWNER TO postgres;

--
-- Name: primarykey_sequence; Type: SEQUENCE; Schema: public; Owner: lyvcuser
--

CREATE SEQUENCE primarykey_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.primarykey_sequence OWNER TO lyvcuser;

--
-- Name: primarykey_sequence; Type: SEQUENCE SET; Schema: public; Owner: lyvcuser
--

SELECT pg_catalog.setval('primarykey_sequence', 423, true);


--
-- Name: usergroup; Type: TABLE; Schema: public; Owner: lyvcuser; Tablespace: 
--

CREATE TABLE usergroup (
    id bigint NOT NULL,
    groupname character varying(200) NOT NULL,
    groupdesc character varying(400) NOT NULL,
    creatorid bigint NOT NULL,
    groupmanager bigint DEFAULT 0
);


ALTER TABLE public.usergroup OWNER TO lyvcuser;

--
-- Name: users; Type: TABLE; Schema: public; Owner: lyvcuser; Tablespace: 
--

CREATE TABLE users (
    accountname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    lastname character varying(200) NOT NULL,
    email character varying(200) NOT NULL,
    id bigint NOT NULL,
    "password" character varying(200) NOT NULL
);


ALTER TABLE public.users OWNER TO lyvcuser;

--
-- Data for Name: groupmember; Type: TABLE DATA; Schema: public; Owner: lyvcuser
--

COPY groupmember (groupid, memberid) FROM stdin;
0	1
0	2
\.


--
-- Data for Name: usergroup; Type: TABLE DATA; Schema: public; Owner: lyvcuser
--

COPY usergroup (id, groupname, groupdesc, creatorid, groupmanager) FROM stdin;
1	默认的组	默认的组	0	0
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: lyvcuser
--

COPY users (accountname, firstname, lastname, email, id, "password") FROM stdin;
lyvcadmin	系统管理员		lyvcadmin@fulong.com.cn	2	123456
\.


--
-- Name: group_pk; Type: CONSTRAINT; Schema: public; Owner: lyvcuser; Tablespace: 
--

ALTER TABLE ONLY usergroup
    ADD CONSTRAINT group_pk PRIMARY KEY (id);


ALTER INDEX public.group_pk OWNER TO lyvcuser;

--
-- Name: groupuser_pk; Type: CONSTRAINT; Schema: public; Owner: lyvcuser; Tablespace: 
--

ALTER TABLE ONLY groupmember
    ADD CONSTRAINT groupuser_pk PRIMARY KEY (groupid, memberid);


ALTER INDEX public.groupuser_pk OWNER TO lyvcuser;

--
-- Name: user_pk; Type: CONSTRAINT; Schema: public; Owner: lyvcuser; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT user_pk PRIMARY KEY (id);


ALTER INDEX public.user_pk OWNER TO lyvcuser;

--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

\connect servermonitor

--
-- PostgreSQL database dump
--

SET client_encoding = 'UNICODE';
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'Standard public schema';


SET search_path = public, pg_catalog;


--
-- Name: plpgsql_call_handler(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plpgsql_call_handler() RETURNS language_handler
    AS '$libdir/plpgsql', 'plpgsql_call_handler'
    LANGUAGE c;


ALTER FUNCTION public.plpgsql_call_handler() OWNER TO postgres;

--
-- Name: plpgsql_validator(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plpgsql_validator(oid) RETURNS void
    AS '$libdir/plpgsql', 'plpgsql_validator'
    LANGUAGE c;


ALTER FUNCTION public.plpgsql_validator(oid) OWNER TO postgres;

--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: public; Owner: 
--

CREATE TRUSTED PROCEDURAL LANGUAGE plpgsql HANDLER plpgsql_call_handler VALIDATOR plpgsql_validator;


--
-- Name: database_size(name); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION database_size(name) RETURNS bigint
    AS '$libdir/dbsize', 'database_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.database_size(name) OWNER TO postgres;

--
-- Name: pg_database_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_database_size(oid) RETURNS bigint
    AS '$libdir/dbsize', 'pg_database_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_database_size(oid) OWNER TO postgres;

--
-- Name: pg_dir_ls(text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_dir_ls(text, boolean) RETURNS SETOF text
    AS '$libdir/admin', 'pg_dir_ls'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_dir_ls(text, boolean) OWNER TO postgres;

--
-- Name: pg_file_length(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_length(text) RETURNS bigint
    AS $_$SELECT len FROM pg_file_stat($1) AS s(len int8, c timestamp, a timestamp, m timestamp, i bool)$_$
    LANGUAGE sql STRICT;


ALTER FUNCTION public.pg_file_length(text) OWNER TO postgres;

--
-- Name: pg_file_read(text, bigint, bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_read(text, bigint, bigint) RETURNS text
    AS '$libdir/admin', 'pg_file_read'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_read(text, bigint, bigint) OWNER TO postgres;

--
-- Name: pg_file_rename(text, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_rename(text, text, text) RETURNS boolean
    AS '$libdir/admin', 'pg_file_rename'
    LANGUAGE c;


ALTER FUNCTION public.pg_file_rename(text, text, text) OWNER TO postgres;

--
-- Name: pg_file_rename(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_rename(text, text) RETURNS boolean
    AS $_$SELECT pg_file_rename($1, $2, NULL); $_$
    LANGUAGE sql STRICT;


ALTER FUNCTION public.pg_file_rename(text, text) OWNER TO postgres;

--
-- Name: pg_file_stat(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_stat(text) RETURNS record
    AS '$libdir/admin', 'pg_file_stat'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_stat(text) OWNER TO postgres;

--
-- Name: pg_file_unlink(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_unlink(text) RETURNS boolean
    AS '$libdir/admin', 'pg_file_unlink'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_unlink(text) OWNER TO postgres;

--
-- Name: pg_file_write(text, text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_write(text, text, boolean) RETURNS bigint
    AS '$libdir/admin', 'pg_file_write'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_file_write(text, text, boolean) OWNER TO postgres;

--
-- Name: pg_logdir_ls(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_logdir_ls() RETURNS SETOF record
    AS '$libdir/admin', 'pg_logdir_ls'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_logdir_ls() OWNER TO postgres;

--
-- Name: pg_postmaster_starttime(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_postmaster_starttime() RETURNS timestamp without time zone
    AS '$libdir/admin', 'pg_postmaster_starttime'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_postmaster_starttime() OWNER TO postgres;

--
-- Name: pg_relation_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_relation_size(oid) RETURNS bigint
    AS '$libdir/dbsize', 'pg_relation_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_relation_size(oid) OWNER TO postgres;

--
-- Name: pg_reload_conf(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_reload_conf() RETURNS integer
    AS '$libdir/admin', 'pg_reload_conf'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.pg_reload_conf() OWNER TO postgres;

--
-- Name: pg_size_pretty(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_size_pretty(bigint) RETURNS text
    AS '$libdir/dbsize', 'pg_size_pretty'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_size_pretty(bigint) OWNER TO postgres;

--
-- Name: pg_tablespace_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_tablespace_size(oid) RETURNS bigint
    AS '$libdir/dbsize', 'pg_tablespace_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.pg_tablespace_size(oid) OWNER TO postgres;

--
-- Name: relation_size(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION relation_size(text) RETURNS bigint
    AS '$libdir/dbsize', 'relation_size'
    LANGUAGE c STRICT;


ALTER FUNCTION public.relation_size(text) OWNER TO postgres;


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: con_mem_online; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE con_mem_online (
    conference_id bigint NOT NULL,
    user_id bigint NOT NULL,
    enter_time timestamp without time zone
);


ALTER TABLE public.con_mem_online OWNER TO postgres;

--
-- Name: pg_logdir_ls; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW pg_logdir_ls AS
    SELECT a.filetime, a.filename FROM pg_logdir_ls() a(filetime timestamp without time zone, filename text);


ALTER TABLE public.pg_logdir_ls OWNER TO postgres;

--
-- Name: primarykey_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE primarykey_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.primarykey_sequence OWNER TO postgres;

--
-- Name: primarykey_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('primarykey_sequence', 2036, true);


--
-- Name: server_state; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE server_state (
    start_time timestamp without time zone NOT NULL,
    state bigint DEFAULT 0,
    stop_time timestamp without time zone
);


ALTER TABLE public.server_state OWNER TO postgres;

--
-- Name: user_state; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_state (
    id bigint NOT NULL,
    enter_time timestamp without time zone
);


ALTER TABLE public.user_state OWNER TO postgres;

--
-- Data for Name: con_mem_online; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY con_mem_online (conference_id, user_id, enter_time) FROM stdin;
\.


--
-- Data for Name: server_state; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY server_state (start_time, state, stop_time) FROM stdin;
\.



--
-- Name: pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY con_mem_online
    ADD CONSTRAINT pkey PRIMARY KEY (conference_id, user_id);


ALTER INDEX public.pkey OWNER TO postgres;

--
-- Name: server_state_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY server_state
    ADD CONSTRAINT server_state_pkey PRIMARY KEY (start_time);


ALTER INDEX public.server_state_pkey OWNER TO postgres;

--
-- Name: user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_state
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


ALTER INDEX public.user_pkey OWNER TO postgres;

--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--
