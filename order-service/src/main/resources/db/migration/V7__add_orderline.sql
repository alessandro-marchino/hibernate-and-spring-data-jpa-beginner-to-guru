DROP TABLE IF EXISTS order_line;

CREATE TABLE order_line (
  id BIGINT NOT NULL AUTO_INCREMENT,
  quantity_ordered int,
  order_header_id bigint,
  created_date timestamp,
  last_modified_date timestamp,
  PRIMARY KEY (id),
  constraint order_header_fk foreign key (order_header_id) references order_header(id)
);
