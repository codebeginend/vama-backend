create table categories(
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    parent_id integer,
    is_published boolean default true,
    CONSTRAINT categories_pkey PRIMARY KEY (id)
);

create table products(
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    code character varying(20),
    logo character varying(255),
    description character varying(255),
    category_id BIGINT,
    unit character varying(255),
    unit_type character varying(255),
    unit_value decimal,
    price decimal,
    opt_price decimal default 0,
    discount decimal,
    stock decimal,
    is_published boolean default true,
    CONSTRAINT products_pkey PRIMARY KEY (id),
    CONSTRAINT products_categories_fk FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);

INSERT INTO categories(name)
VALUES ('Бытовая химия');
INSERT INTO categories(name)
VALUES ('Средства личной гигиены');

INSERT INTO categories(name, parent_id)
VALUES ('Моющие средства для стирки', 1);
INSERT INTO categories(name, parent_id)
VALUES ('Порошки', 1);
INSERT INTO categories(name, parent_id)
VALUES ('Мыло', 1);
INSERT INTO categories(name, parent_id)
VALUES ('Пятновыводители', 1);

INSERT INTO categories(name, parent_id)
VALUES ('Салфетки', 2);
INSERT INTO categories(name, parent_id)
VALUES ('Жидкое мыло', 2);
INSERT INTO categories(name, parent_id)
VALUES ('Крема', 2);
INSERT INTO categories(name, parent_id)
VALUES ('Для полости рта', 2);

INSERT INTO products(name, logo, description, category_id, unit, unit_type, unit_value, price, discount, stock)
VALUES ('Стиральный порошок Tide 900',
        'https://magnitcosmetic.ru/upload/iblock/4d4/4d4417dad1e95edf4f964e0b21620568.jpeg',
        'Стиральный порошок Tide',
        3,
        'VOLUME',
        'MILLILITER',
        900,
        120,
        50,
        20);
INSERT INTO products(name, logo, description, category_id, unit, unit_type, unit_value, price, discount, stock)
VALUES ('Стиральный порошок Tide 800',
        'https://magnitcosmetic.ru/upload/iblock/4d4/4d4417dad1e95edf4f964e0b21620568.jpeg',
        'Стиральный порошок Tide',
        3,
        'VOLUME',
        'MILLILITER',
        800,
        100,
        50,
        10);
INSERT INTO products(name, logo, description, category_id, unit, unit_type, unit_value, price, discount, stock)
VALUES ('Стиральный порошок Tide 1200',
        'https://magnitcosmetic.ru/upload/iblock/4d4/4d4417dad1e95edf4f964e0b21620568.jpeg',
        'Стиральный порошок Tide',
        3,
        'VOLUME',
        'MILLILITER',
        1200,
        200,
        50,
        4);
INSERT INTO products(name, logo, description, category_id, unit, unit_type, unit_value, price, discount, stock)
VALUES ('Стиральный порошок Tide 1300',
        'https://magnitcosmetic.ru/upload/iblock/4d4/4d4417dad1e95edf4f964e0b21620568.jpeg',
        'Стиральный порошок Tide',
        3,
        'VOLUME',
        'MILLILITER',
        900,
        300,
        50,
        100);
INSERT INTO products(name, logo, description, category_id, unit, unit_type, unit_value, price, discount, stock)
VALUES ('Стиральный порошок Tide 1400',
        'https://magnitcosmetic.ru/upload/iblock/4d4/4d4417dad1e95edf4f964e0b21620568.jpeg',
        'Стиральный порошок Tide',
        3,
        'VOLUME',
        'MILLILITER',
        1400,
        400,
        50,
        30);
INSERT INTO products(name, logo, description, category_id, unit, unit_type, unit_value, price, discount, stock)
VALUES ('Стиральный порошок Tide 1500',
        'https://magnitcosmetic.ru/upload/iblock/4d4/4d4417dad1e95edf4f964e0b21620568.jpeg',
        'Стиральный порошок Tide',
        3,
        'VOLUME',
        'MILLILITER',
        1500,
        500,
        50,
        11);