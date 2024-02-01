create table product_stock_details(
    id bigserial NOT NULL,
    price decimal,
    discount decimal,
    stock decimal,
    product_id BIGINT,
        CONSTRAINT product_stock_details_pkey PRIMARY KEY (id),
        CONSTRAINT product_stock_details_with_products_fk FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE SET NULL
);

INSERT INTO product_stock_details(price, discount, stock, product_id)
VALUES (1000, 50, 20, 1);
INSERT INTO product_stock_details(price, discount, stock, product_id)
VALUES (1500, 50, 10, 1);
INSERT INTO product_stock_details(price, discount, stock, product_id)
VALUES (2000, 100, 5, 1);
INSERT INTO product_stock_details(price, discount, stock, product_id)
VALUES (2200, 100, 0, 1);