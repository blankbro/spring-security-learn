package io.github.blankbro.authorizationserversocial.social.wechat.connect;

import org.junit.Test;
import org.springframework.social.oauth2.OAuth2Parameters;

public class WeChatOAuth2TemplateTest {

    @Test
    public void test202312181315() {
        WeChatOAuth2Template weChatOAuth2Template = new WeChatOAuth2Template(
                "qwe",
                "qwe",
                WeChatOAuth2Template.OFFIACCOUNT_AUTHORIZE_URL
        );

        OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
        oAuth2Parameters.setRedirectUri("http://10.10.37.128:8080/auth/we-chat");

        String buildAuthorizeUrl = weChatOAuth2Template.buildAuthorizeUrl(oAuth2Parameters);
        System.out.println(buildAuthorizeUrl);
    }
}
