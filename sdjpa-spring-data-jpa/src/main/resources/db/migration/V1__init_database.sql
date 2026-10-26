DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;

CREATE TABLE book (
  id BIGINT NOT NULL AUTO_INCREMENT,
  isbn VARCHAR(255),
  publisher VARCHAR(255),
  title VARCHAR(255),
  author_id BIGINT,
  PRIMARY KEY (id)
);
CREATE TABLE author(
  id BIGINT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  PRIMARY KEY (id)
);

ALTER TABLE book ADD CONSTRAINT book_author_fk FOREIGN KEY (author_id) REFERENCES author (id);

INSERT INTO author (first_name, last_name)
SELECT tmp.first_name, tmp.last_name
FROM (VALUES
	ROW ('Craig', 'Walls'),
	ROW ('Robert', 'Martin')
) AS tmp(first_name, last_name);

INSERT INTO book (isbn, publisher, title, author_id)
SELECT tmp.isbn, tmp.publisher, tmp.title, author.id
FROM (VALUES
  ROW ('978-1617294945', 'Simon & Schuster', 'Spring in Action, 5th Edition', 'Craig', 'Walls'),
  ROW ('978-1617292545', 'Simon & Schuster', 'Spring Boot in Action, 1st Edition', 'Craig', 'Walls'),
  ROW ('978-1617297571', 'Simon & Schuster', 'Spring in Action, 6th Edition', 'Craig', 'Walls'),
  ROW ('978-0321125217', 'Addison Wesley', 'Domain-Driven Design', 'Robert', 'Martin'),
  ROW ('978-0134494166', 'Addison Wesley', 'Clean Code', 'Robert', 'Martin')
) AS tmp(isbn, publisher, title, author_first_name, author_last_name)
JOIN author ON author.first_name = tmp.author_first_name AND author.last_name = tmp.author_last_name;
