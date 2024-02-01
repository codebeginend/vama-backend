create table orders(
    id bigserial NOT NULL,
    user_id BIGINT,
    address character varying(255),
    entrance character varying(255),
    delivery_type character varying(255),
    payment_type character varying(255),
    payment_status character varying(255),
        CONSTRAINT orders_pkey PRIMARY KEY (id),
        CONSTRAINT order_items_with_users_fk FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

create table order_statuses(
    id bigserial NOT NULL,
    name character varying(255),
    order_id BIGINT,
    user_id BIGINT,
    created_at timestamp without time zone default current_date,
        CONSTRAINT order_statuses_pkey PRIMARY KEY (id),
        CONSTRAINT order_items_with_orders_fk FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE SET NULL,
        CONSTRAINT order_items_with_users_fk FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);


create table order_items(
    id bigserial NOT NULL,
    product_id BIGINT,
    order_id BIGINT,
    price decimal,
    discount decimal,
    count decimal,
        CONSTRAINT order_items_pkey PRIMARY KEY (id),
        CONSTRAINT order_items_with_orders_fk FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE SET NULL,
        CONSTRAINT order_items_with_products_fk FOREIGN KEY (product_id) REFERENCES products(id)
);