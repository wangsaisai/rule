CREATE TABLE IF NOT EXISTS easy_rule (
  id serial PRIMARY KEY,
  name character varying(128) UNIQUE,
  priority int,
  type character varying(32),
  description character varying(512),
  condition text,
  actions text
);

CREATE INDEX IF NOT EXISTS idx_easy_rule_type ON easy_rule (type);