package io.github.blankbro.authorizationserversocial.social.wechat.config;

import io.github.blankbro.authorizationserversocial.social.wechat.connect.WeChatOAuth2ConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.UserIdSource;
import org.springframework.social.autoconfigure.SocialAutoConfigurerAdapter;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

@EnableSocial
@Configuration
public class WeChatSocialConfiguration extends SocialAutoConfigurerAdapter {

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        return new WeChatOAuth2ConnectionFactory(
                "we-chat",
                "123",
                "123"
        );
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }
}
