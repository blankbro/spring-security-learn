package io.github.blankbro.authorizationserversocial.social.wechat.connect;


import lombok.Getter;
import lombok.Setter;
import org.springframework.social.oauth2.AccessGrant;

/**
 * 对微信access_token信息的封装
 * 与标准的OAuth2协议不同，微信在获取access_token时会同时返回openId，并没有单独的通过accessToke换取openId的服务
 * 在此处继承标准AccessGrant（Spring提供的令牌封装类），添加openId字段
 */
@Setter
@Getter
public class WeChatAccessGrant extends AccessGrant {

    private String openId;

    public WeChatAccessGrant(String openId, String accessToken, String scope, String refreshToken, Long expiresIn) {
        super(accessToken, scope, refreshToken, expiresIn);
        this.openId = openId;
    }
}
