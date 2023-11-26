CREATE DATABASE security_learn DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE TABLE t_user (
    id int primary key,
    username varchar(10) not null default '',
    password varchar(10) not null default ''
);