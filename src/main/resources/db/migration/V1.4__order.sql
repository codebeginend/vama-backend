-- create table order_items(
--     id bigserial NOT NULL,
--     count decimal,
--     product_stock_detail_id bigserial,
--         CONSTRAINT order_items_pkey PRIMARY KEY (id),
--         CONSTRAINT order_items_with_product_stock_details_fk FOREIGN KEY (product_stock_detail_id) REFERENCES product_stock_details(id) ON DELETE SET NULL
-- );