CREATE TABLE IF NOT EXISTS employee
(
    id CHAR(9) NOT NULL,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    wage DECIMAL(19,2),
    event_time TIMESTAMP NOT NULL,
    CONSTRAINT pk_employee PRIMARY KEY (name,surname)
);
