package io.github.blankbro.authorizationserversocial.social.wechat.oauth2;


import io.github.blankbro.authorizationserversocial.social.framework.core.oauth2.AccessGrant;

/**
 * 对微信access_token信息的封装
 * 与标准的OAuth2协议不同，微信在获取access_token时会同时返回openId，并没有单独的通过accessToke换取openId的服务
 * 在此处继承标准AccessGrant（Spring提供的令牌封装类），添加openId字段
 */
public class WeChatAccessGrant extends AccessGrant {

    private String openId;

    public WeChatAccessGrant() {
        super("");
    }

    public WeChatAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
        super(accessToken, scope, refreshToken, expiresIn);
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
