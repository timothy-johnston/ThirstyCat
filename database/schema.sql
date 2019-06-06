BEGIN;

-- CREATE statements go here
DROP TABLE IF EXISTS drinks;
DROP TABLE IF EXISTS images;

CREATE TABLE drinks (
  id SERIAL PRIMARY KEY,
  start_date TIMESTAMP NOT NULL,
  end_date TIMESTAMP NOT NULL
--  ,CONSTRAINT fk_default_city_id FOREIGN KEY (default_city_id) REFERENCES city (id)  --TODO: read more about constraints
);

CREATE TABLE images (
  id SERIAL PRIMARY KEY,
  drink_id BIGINT NOT NULL,
  image BYTEA
  -- CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES app_user (id) --TODO: read more about constraints
);

COMMIT;