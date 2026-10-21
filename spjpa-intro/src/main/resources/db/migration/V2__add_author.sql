DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS author_seq;

CREATE TABLE author(
  id BIGINT NOT NULL,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  PRIMARY KEY (id)
);
CREATE TABLE author_seq(
  next_val BIGINT
);

INSERT INTO author_seq(next_val) VALUES (1);
