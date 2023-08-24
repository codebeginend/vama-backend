create table product_stock_details(
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    price decimal,
    discount decimal,
    stock decimal,
    product_id bigserial,
        CONSTRAINT product_stock_details_pkey PRIMARY KEY (id),
        CONSTRAINT product_stock_details_with_products_fk FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE SET NULL
);

INSERT INTO product_stock_details(name, price, discount, stock, product_id)
VALUES ('900 мл.', 1000, 50, 20, 1);
INSERT INTO product_stock_details(name, price, discount, stock, product_id)
VALUES ('1200 мл.', 1500, 50, 10, 1);
INSERT INTO product_stock_details(name, price, discount, stock, product_id)
VALUES ('1600 мл.', 2000, 100, 5, 1);
INSERT INTO product_stock_details(name, price, discount, stock, product_id)
VALUES ('2100 мл.', 2200, 100, 0, 1);