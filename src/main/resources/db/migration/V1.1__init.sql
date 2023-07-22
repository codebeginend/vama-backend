CREATE TABLE roles
(
    id bigserial NOT NULL,
    name character varying(255),
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);

CREATE TABLE users
(
    id bigserial NOT NULL,
    name character varying(255),
    logo character varying(255),
    username character varying(255) UNIQUE NOT NULL,
    password character varying(255),
    role_id bigint NOT NULL,
    created_at timestamp without time zone default current_date,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT fkUsersRoles FOREIGN KEY (role_id)
        REFERENCES roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE verification_codes
(
    id bigserial NOT NULL,
    phone_number character varying(20),
    code character varying(6),
    expires_at timestamp with time zone,
    is_used boolean default false,
    created_at timestamp without time zone default current_date,
    CONSTRAINT verification_codes_pkey PRIMARY KEY (id)
);

-- ROLES
INSERT INTO roles(name)
    VALUES ('ROLE_ADMIN');
INSERT INTO roles(name)
    VALUES ('ROLE_USER');