create table category (
  id bigint not null auto_increment primary key,
  description varchar(50),
  created_date timestamp,
  last_modified_date timestamp
);
create table product_category(
  product_id bigint not null,
  category_id bigint not null,
  constraint pc_product_id_fk foreign key (product_id) references product(id),
  constraint pc_category_id_fk foreign key (category_id) references category(id)
);

INSERT INTO category(description, created_date, last_modified_date) values ('CAT1', now(), now());
INSERT INTO category(description, created_date, last_modified_date) values ('CAT2', now(), now());
INSERT INTO category(description, created_date, last_modified_date) values ('CAT3', now(), now());

INSERT INTO product_category(product_id, category_id)
SELECT p.id, c.id
FROM (VALUES
  ROW('Product 2', 'CAT1'),
  ROW('Product 1', 'CAT3'),
  ROW('Product 4', 'CAT3')
) AS tmp(prod, cate)
join product p on p.description = tmp.prod
join category c on c.description = tmp.cate;
