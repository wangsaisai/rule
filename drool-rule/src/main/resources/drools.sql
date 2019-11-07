CREATE TABLE IF NOT EXISTS drool_rule (
  id serial PRIMARY KEY,
  name character varying(128) UNIQUE,
  priority int,
  type character varying(32),
  content text
);

CREATE INDEX IF NOT EXISTS idx_drool_rule_type ON drool_rule (type);