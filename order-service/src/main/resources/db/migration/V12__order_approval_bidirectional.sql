ALTER TABLE order_approval ADD COLUMN order_header_id BIGINT;

ALTER TABLE order_approval ADD CONSTRAINT order_approval_header_fk FOREIGN KEY(order_header_id) REFERENCES order_header(id);
