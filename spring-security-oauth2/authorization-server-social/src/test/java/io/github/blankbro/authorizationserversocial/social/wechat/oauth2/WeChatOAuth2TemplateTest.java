package io.github.blankbro.authorizationserversocial.social.wechat.oauth2;

import io.github.blankbro.authorizationserversocial.social.wechat.connect.WeChatOAuth2Template;
import org.junit.Test;
import org.springframework.social.oauth2.OAuth2Parameters;

public class WeChatOAuth2TemplateTest {

    @Test
    public void test202312181315() {
        /**
         * 秘钥来源，好像没啥用
         * https://zhuanlan.zhihu.com/p/32983438
         */
        WeChatOAuth2Template weChatOAuth2Template = new WeChatOAuth2Template("wxfd6965ab1fc6adb2", "66bb4566de776ac699ec1dbed0cc3dd1");
        OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
        // 整不了
        oAuth2Parameters.setRedirectUri("http://www.merryyou.cn");
        String buildAuthorizeUrl = weChatOAuth2Template.buildAuthorizeUrl(oAuth2Parameters);
        System.out.println(buildAuthorizeUrl);
    }
}
