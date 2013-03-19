# UserActions schema

# --- !Ups

CREATE TABLE UserActions (
  action bigint NOT NULL
);

# --- !Downs

DROP TABLE UserActions;
