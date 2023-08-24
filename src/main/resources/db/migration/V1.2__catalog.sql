create table categories(
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    parent_id integer,
    CONSTRAINT categories_pkey PRIMARY KEY (id)
);

create table products(
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    logo character varying(255) NOT NULL,
    description character varying(255) NOT NULL,
    category_id bigserial,
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

INSERT INTO products(name, logo, description, category_id)
VALUES ('Стиральный порошок Tide',
        'https://magnitcosmetic.ru/upload/iblock/4d4/4d4417dad1e95edf4f964e0b21620568.jpeg',
        'Стиральный порошок Tide',
        3);
INSERT INTO products(name, logo, description, category_id)
VALUES ('Стиральный порошок Tide',
        'https://magnitcosmetic.ru/upload/iblock/4d4/4d4417dad1e95edf4f964e0b21620568.jpeg',
        'Стиральный порошок Tide',
        3);
INSERT INTO products(name, logo, description, category_id)
VALUES ('Стиральный порошок Tide',
        'https://magnitcosmetic.ru/upload/iblock/4d4/4d4417dad1e95edf4f964e0b21620568.jpeg',
        'Стиральный порошок Tide',
        3);
INSERT INTO products(name, logo, description, category_id)
VALUES ('Стиральный порошок Tide',
        'https://magnitcosmetic.ru/upload/iblock/4d4/4d4417dad1e95edf4f964e0b21620568.jpeg',
        'Стиральный порошок Tide',
        3);
INSERT INTO products(name, logo, description, category_id)
VALUES ('Стиральный порошок Tide',
        'https://magnitcosmetic.ru/upload/iblock/4d4/4d4417dad1e95edf4f964e0b21620568.jpeg',
        'Стиральный порошок Tide',
        3);
INSERT INTO products(name, logo, description, category_id)
VALUES ('Стиральный порошок Tide',
        'https://magnitcosmetic.ru/upload/iblock/4d4/4d4417dad1e95edf4f964e0b21620568.jpeg',
        'Стиральный порошок Tide',
        3);