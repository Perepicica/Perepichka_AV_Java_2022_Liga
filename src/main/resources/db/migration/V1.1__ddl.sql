CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE SCHEMA IF NOT EXISTS db_main;

CREATE TABLE employee
(
    id        VARCHAR(36),
    name       VARCHAR NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE task
(
    id     VARCHAR(36),
    header      VARCHAR NOT NULL,
    description     VARCHAR NOT NULL,
    deadline        date NOT NULL ,
    employee_id VARCHAR(36),
    status varchar(16),
    PRIMARY KEY (id),
    FOREIGN KEY (employee_id) REFERENCES employee (id)
);

