package io.github.blankbro.authorizationserversocial.social.wechat.oauth2;

import io.github.blankbro.authorizationserversocial.social.wechat.connect.WeChatOAuth2Template;
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
        oAuth2Parameters.setRedirectUri("http://10.10.39.255");

        String buildAuthorizeUrl = weChatOAuth2Template.buildAuthorizeUrl(oAuth2Parameters);
        System.out.println(buildAuthorizeUrl);
    }
}
