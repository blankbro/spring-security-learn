package io.github.blankbro.authorizationserversocial.social.framework.core.connect;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConnectionData implements Serializable {

    private String providerId;
    private String providerUserId;
    private String nickname;
    private String email;
    private String mobile;
    private String position;
    private String avatar;
    private Integer status;
    private Long[] department;
    private Long[] deptLeader;
    private String accessToken;
    private String secret;
    private String refreshToken;
    private Long expireTime;
}
