CREATE SEQUENCE gen_seq;
CREATE TABLE points (
    id serial NOT NULL UNIQUE,
    owner VARCHAR,
    x DOUBLE PRECISION,
    y DOUBLE PRECISION,
    r DOUBLE PRECISION,
    coordsStatus BOOLEAN,
    bornDate TIMESTAMP
);