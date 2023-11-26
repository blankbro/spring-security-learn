# mysql -hlocalhost -P3306 -uroot -proot;

CREATE DATABASE security_learn DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE TABLE t_user
(
    id       int AUTO_INCREMENT PRIMARY KEY,
    username varchar(10) UNIQUE NOT NULL DEFAULT '',
    password varchar(10)        NOT NULL DEFAULT ''
);

INSERT INTO t_user (username, password)
VALUES ('admin', '123'),
       ('root', '456');