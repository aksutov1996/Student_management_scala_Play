# --- !Ups

CREATE TABLE students (
  id SERIAL PRIMARY KEY,
  last_name VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  middle_name VARCHAR(255),
  "group" VARCHAR(50) NOT NULL,
  average_mark DOUBLE PRECISION NOT NULL
);


# --- !Downs

DROP TABLE students;