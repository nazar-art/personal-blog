CREATE TABLE "persistent_logions"(
  "username" VARCHAR(65) NOT NULL UNIQUE,
  "series" VARCHAR(65) NOT NULL,
  "token" VARCHAR(65) NOT NULL,
  "last_used" TIMESTAMP NOT NULL,
  updated_at TIMESTAMP DEFAULT 'now'::timestamp,
  PRIMARY KEY ("series")
);