ALTER TABLE products
    ADD is_popular boolean default false;

create table order_delivery_time(
    id bigserial NOT NULL,
    day_of_week character varying(10),
    times time without time zone[][],
    is_delivery boolean DEFAULT false,
    CONSTRAINT delivery_time PRIMARY KEY (id)
);

ALTER TABLE orders
    ADD delivery_date date;
ALTER TABLE orders
    ADD delivery_time time without time zone[];

ALTER TABLE products
    ADD union_products bigint[] DEFAULT '{}';