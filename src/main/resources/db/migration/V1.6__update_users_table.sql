ALTER TABLE users
    ADD is_active boolean default true;

ALTER TABLE users
    ADD is_delete boolean default false