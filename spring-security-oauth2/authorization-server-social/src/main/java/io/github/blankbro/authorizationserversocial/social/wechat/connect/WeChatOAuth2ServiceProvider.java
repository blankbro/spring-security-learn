package io.github.blankbro.authorizationserversocial.social.wechat.connect;

import io.github.blankbro.authorizationserversocial.social.wechat.api.WeChatApi;
import io.github.blankbro.authorizationserversocial.social.wechat.api.impl.WeChatApiImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * 微信的OAuth2流程处理器的提供器，供spring social的connect体系调用
 */
public class WeChatOAuth2ServiceProvider extends AbstractOAuth2ServiceProvider<WeChatApi> {

    /**
     * @param appId
     * @param appSecret
     */
    public WeChatOAuth2ServiceProvider(String appId, String appSecret) {
        super(new WeChatOAuth2Template(appId, appSecret, WeChatOAuth2Template.OFFIACCOUNT_AUTHORIZE_URL));
    }

    /**
     * @param accessToken
     * @return
     */
    @Override
    public WeChatApi getApi(String accessToken) {
        return new WeChatApiImpl(accessToken);
    }
}
