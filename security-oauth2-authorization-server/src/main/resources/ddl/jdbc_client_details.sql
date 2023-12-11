-- 建表语句来自：https://github.com/spring-attic/spring-security-oauth/blob/main/spring-security-oauth2/src/test/resources/schema.sql
-- 注意: 并用 BLOB 替换语句中的 LONGVARBINARY 类型

-- used in tests that use HSQL
create table oauth_client_details
(
    -- 客户端唯一标识
    client_id               VARCHAR(256) PRIMARY KEY,
    -- 客户端拥有的资源
    resource_ids            VARCHAR(256),
    -- 客户端安全码
    client_secret           VARCHAR(256),
    -- 用来限制客户端访问范围，如果为空的话，name 客户端拥有全部访问范围
    scope                   VARCHAR(256),
    -- 客户端可以使用的授权类型。目前已知的有：authorization_code,implicit,password,client_credentials,refresh_token。用逗号隔开
    authorized_grant_types  VARCHAR(256),
    -- 回调地址。授权服务会往该回调地址推送此客户端相关的信息。
    web_server_redirect_uri VARCHAR(256),
    -- 客户端可以使用的权限（基于Spring Security authorities）
    authorities             VARCHAR(256),
    -- access_token 有效期
    access_token_validity   INTEGER,
    -- refresh_token 有效期
    refresh_token_validity  INTEGER,
    additional_information  VARCHAR(4096),
    -- 自动批准。true：表示用户默认同意；false：需要用户自己点击授权。
    autoapprove             VARCHAR(256)
);

-- 测试数据
INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`)
VALUES
    ('client_01', 'resource-id-01', '$2a$10$wqWbuBr.pLHQbxWk32rQ3eOSULEHm9VIX/hWyTkO0Z47MHUByvDK2', 'resource-scope-01', 'authorization_code,implicit,password,client_credentials,refresh_token', 'https://www.baidu.com', NULL, NULL, NULL, NULL, 'false');


create table oauth_client_token
(
    token_id          VARCHAR(256),
    token             BLOB,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256)
);

create table oauth_access_token
(
    token_id          VARCHAR(256),
    token             BLOB,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256),
    authentication    BLOB,
    refresh_token     VARCHAR(256)
);

create table oauth_refresh_token
(
    token_id       VARCHAR(256),
    token          BLOB,
    authentication BLOB
);

create table oauth_code
(
    code           VARCHAR(256),
    authentication BLOB
);

create table oauth_approvals
(
    userId         VARCHAR(256),
    clientId       VARCHAR(256),
    scope          VARCHAR(256),
    status         VARCHAR(10),
    expiresAt      TIMESTAMP,
    lastModifiedAt TIMESTAMP
);
-- 建表语句来自：https://github.com/spring-attic/spring-security-oauth/blob/main/spring-security-oauth2/src/test/resources/schema.sql
-- 注意: 并用 BLOB 替换语句中的 LONGVARBINARY 类型

-- used in tests that use HSQL
create table oauth_client_details
(
    client_id               VARCHAR(256) PRIMARY KEY,
    resource_ids            VARCHAR(256),
    client_secret           VARCHAR(256),
    scope                   VARCHAR(256),
    authorized_grant_types  VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities             VARCHAR(256),
    access_token_validity   INTEGER,
    refresh_token_validity  INTEGER,
    additional_information  VARCHAR(4096),
    autoapprove             VARCHAR(256)
);

create table oauth_client_token
(
    token_id          VARCHAR(256),
    token             BLOB,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256)
);

create table oauth_access_token
(
    token_id          VARCHAR(256),
    token             BLOB,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256),
    authentication    BLOB,
    refresh_token     VARCHAR(256)
);

create table oauth_refresh_token
(
    token_id       VARCHAR(256),
    token          BLOB,
    authentication BLOB
);

create table oauth_code
(
    code           VARCHAR(256),
    authentication BLOB
);

create table oauth_approvals
(
    userId         VARCHAR(256),
    clientId       VARCHAR(256),
    scope          VARCHAR(256),
    status         VARCHAR(10),
    expiresAt      TIMESTAMP,
    lastModifiedAt TIMESTAMP
);


-- customized oauth_client_details table
create table ClientDetails
(
    appId                  VARCHAR(256) PRIMARY KEY,
    resourceIds            VARCHAR(256),
    appSecret              VARCHAR(256),
    scope                  VARCHAR(256),
    grantTypes             VARCHAR(256),
    redirectUrl            VARCHAR(256),
    authorities            VARCHAR(256),
    access_token_validity  INTEGER,
    refresh_token_validity INTEGER,
    additionalInformation  VARCHAR(4096),
    autoApproveScopes      VARCHAR(256)
);

-- customized oauth_client_details table
create table ClientDetails
(
    appId                  VARCHAR(256) PRIMARY KEY,
    resourceIds            VARCHAR(256),
    appSecret              VARCHAR(256),
    scope                  VARCHAR(256),
    grantTypes             VARCHAR(256),
    redirectUrl            VARCHAR(256),
    authorities            VARCHAR(256),
    access_token_validity  INTEGER,
    refresh_token_validity INTEGER,
    additionalInformation  VARCHAR(4096),
    autoApproveScopes      VARCHAR(256)
);