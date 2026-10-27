DROP TABLE IF EXISTS order_header;

CREATE TABLE order_header (
  id BIGINT NOT NULL AUTO_INCREMENT,
  customer_name VARCHAR(255),
  PRIMARY KEY (id)
);

INSERT INTO order_header (customer_name) VALUES ('Customer 1');
INSERT INTO order_header (customer_name) VALUES ('Customer 2');
INSERT INTO order_header (customer_name) VALUES ('Customer 3');
INSERT INTO order_header (customer_name) VALUES ('Customer 4');
INSERT INTO order_header (customer_name) VALUES ('Customer 5');
