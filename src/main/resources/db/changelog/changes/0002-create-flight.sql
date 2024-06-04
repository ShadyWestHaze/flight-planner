--liquibase formatted sql
--changeset krish:2

CREATE TABLE FLIGHT
(
    id              SERIAL PRIMARY KEY,
    from_airport_id INTEGER   NOT NULL,
    to_airport_id   INTEGER   NOT NULL,
    departure_time  TIMESTAMP NOT NULL,
    arrival_time    TIMESTAMP NOT NULL,
    carrier         VARCHAR   NOT NULL,
    CONSTRAINT fk_from_airport FOREIGN KEY (from_airport_id) REFERENCES AIRPORT (id),
    CONSTRAINT fk_to_airport FOREIGN KEY (to_airport_id) REFERENCES AIRPORT (id)
);

CREATE SEQUENCE FLIGHT_SEQ START WITH 0 INCREMENT BY 1;
