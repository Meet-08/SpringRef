CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role    VARCHAR(50)
);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_user_roles_on_user FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users
    DROP
        COLUMN role;