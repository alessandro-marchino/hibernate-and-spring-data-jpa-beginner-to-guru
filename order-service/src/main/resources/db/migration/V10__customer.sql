create table customer(
  id bigint not null auto_increment primary key,
  customer_name varchar(50),
  address varchar(30),
  city varchar(30),
  state varchar(30),
  zip_code varchar(30),
  phone varchar(20),
  email varchar(255),
  created_date timestamp,
  last_modified_date timestamp
);

INSERT INTO customer (customer_name, address, city, state, zip_code, phone, email, created_date, last_modified_date)
select distinct oh.customer_name, null, null, null, null, null, null, now(), now()
from order_header oh
where oh.customer_name is not null
and not exists (
  select 1
  from customer curr
  where curr.customer_name = oh.customer_name
);

alter table order_header drop column customer_name;
alter table order_header add customer_id bigint;
alter table order_header add constraint order_header_customer_fk foreign key (customer_id) references customer(id);
