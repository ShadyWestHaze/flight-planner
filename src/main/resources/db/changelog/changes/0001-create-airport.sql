--liquibase formatted sql
--changeset krish:1

CREATE TABLE AIRPORT
(
    id      SERIAL PRIMARY KEY,
    country VARCHAR NOT NULL,
    city    VARCHAR NOT NULL,
    airport VARCHAR NOT NULL

);
