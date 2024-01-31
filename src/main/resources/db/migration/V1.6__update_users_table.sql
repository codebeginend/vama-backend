ALTER TABLE users
    ADD is_active boolean default true;

ALTER TABLE users
    ADD is_delete boolean default false

insert into users (name, username, password, role_id) values ('admin', 'admin', 'admin', 1);