DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS book_seq;

CREATE TABLE book(
  id BIGINT NOT NULL,
  isbn VARCHAR(255),
  publisher VARCHAR(255),
  title VARCHAR(255),
  PRIMARY KEY (id)
);
CREATE TABLE book_seq(
  next_val BIGINT
);

INSERT INTO book_seq(next_val) VALUES (1);
