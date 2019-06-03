-- *************************************************************************************************
-- This script creates all of the database objects (tables, sequences, etc) for the database
-- *************************************************************************************************

BEGIN;

-- CREATE statements go here
DROP TABLE IF EXISTS user_alert;
DROP TABLE IF EXISTS app_user;
DROP TABLE IF EXISTS city;
DROP TABLE IF EXISTS twilio_auth;

/*
CREATE TABLE city(
   city              VARCHAR(46) NOT NULL
  ,city_ascii        VARCHAR(46) NOT NULL
  ,state_id          VARCHAR(2) NOT NULL
  ,state_name        VARCHAR(20) NOT NULL
  ,county_fips       INTEGER  NOT NULL
  ,county_name       VARCHAR(23) NOT NULL
  ,lat               NUMERIC(7,4) NOT NULL
  ,lng               NUMERIC(9,4) NOT NULL
  ,population        NUMERIC(10,1)
  ,population_proper INTEGER 
  ,density           NUMERIC(7,1) NOT NULL
  ,source            VARCHAR(7) NOT NULL
  ,incorporated      VARCHAR(5) NOT NULL
  ,timezone          VARCHAR(30)
  ,zips              VARCHAR(1859)
  ,id                INTEGER  NOT NULL PRIMARY KEY 
);
*/


CREATE TABLE drinks (
  id SERIAL PRIMARY KEY,
  start_date date NOT NULL,
  end_date date NOT NULL
--  ,CONSTRAINT fk_default_city_id FOREIGN KEY (default_city_id) REFERENCES city (id)  #TODO: read more about constraints
);

CREATE TABLE user_alert (
  alert_id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL,
  alert_type VARCHAR(32),
  alerts_above INTEGER,
  alerts_below INTEGER,

  CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES app_user (id)
);


CREATE TABLE twilio_auth (
  account_sid VARCHAR(64) PRIMARY KEY,
  auth_token VARCHAR(64),
  from_phone VARCHAR(16)
);


COMMIT;