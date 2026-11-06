DROP TABLE IF EXISTS product;

CREATE TABLE product (
  id BIGINT NOT NULL AUTO_INCREMENT,
  description VARCHAR(255),
  product_status VARCHAR(30),
  created_date timestamp,
  last_modified_date timestamp,
  PRIMARY KEY (id)
);

INSERT INTO product (description, product_status, created_date, last_modified_date) VALUES ('Product 1', 'NEW', now(), now());
INSERT INTO product (description, product_status, created_date, last_modified_date) VALUES ('Product 2', 'NEW', now(), now());
INSERT INTO product (description, product_status, created_date, last_modified_date) VALUES ('Product 3', 'IN_STOCK', now(), now());
INSERT INTO product (description, product_status, created_date, last_modified_date) VALUES ('Product 4', 'IN_STOCK', now(), now());
INSERT INTO product (description, product_status, created_date, last_modified_date) VALUES ('Product 5', 'DISCONTINUED', now(), now());
