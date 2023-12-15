-- 建表语句来自：org.springframework.security.core.userdetails.jdbc 目录下的 users.ddl
create table users
(
    username varchar(50)  not null primary key,
    password varchar(500) not null,
    enabled  boolean      not null
);

-- 测试数据
INSERT INTO `users` (`username`, `password`, `enabled`)
VALUES ('admin', '$2a$10$wqWbuBr.pLHQbxWk32rQ3eOSULEHm9VIX/hWyTkO0Z47MHUByvDK2', 1),
       ('zexin', '$2a$10$wqWbuBr.pLHQbxWk32rQ3eOSULEHm9VIX/hWyTkO0Z47MHUByvDK2', 1);

create table authorities
(
    username  varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key (username) references users (username)
);
create unique index ix_auth_username on authorities (username, authority);

-- 测试数据
INSERT INTO `authorities` (`username`, `authority`)
VALUES ('admin', 'admin');