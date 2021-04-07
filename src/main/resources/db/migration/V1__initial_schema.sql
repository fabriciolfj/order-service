CREATE table orders (
id bigserial primary key not null,
created_data bigint not null,
last_modified_date bigint not null,
version integer not null,
book_isbn varchar(255) not null,
book_name varchar(255),
book_price float8,
quantity int not null,
status varchar(255) not null
);