alter table order_header
  add column shipping_address VARCHAR(30),
  add column shipping_city VARCHAR(30),
  add column shipping_state VARCHAR(30),
  add column shipping_zip_code VARCHAR(30),
  add column bill_to_address VARCHAR(30),
  add column bill_to_city VARCHAR(30),
  add column bill_to_state VARCHAR(30),
  add column bill_to_zip_code VARCHAR(30);
