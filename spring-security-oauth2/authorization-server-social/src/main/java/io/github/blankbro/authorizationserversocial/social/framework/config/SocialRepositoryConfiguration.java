package io.github.blankbro.authorizationserversocial.social.framework.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * proxyBeanMethods属性定义是否要对配置类中的bean方法进行代理。
 * 默认是true,Spring会对配置类中的bean方法进行JDK动态代理,返回创建好的对象。
 * 设置为false表示不进行代理,直接返回构造的对象实例。
 * proxyBeanMethods=true情况下,调用bean方法每次都会创建新的对象实例。
 * proxyBeanMethods=false情况下,调用bean方法直接返回之前构造的对象实例,每次调用返回的都是同一个对象实例。
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SocialProperties.class)
public class SocialRepositoryConfiguration {

    @Bean
    @ConditionalOnMissingBean(SocialRepository.class)
    SocialRepository socialRepository(SocialProperties properties) {
        Map<String, ClientRegistration> clientRegistrations = SocialPropertiesClientRegistrationAdapter.getClientRegistrations(properties);
        Map<String, Map<String, ClientRegistration>> clientProviders = SocialPropertiesClientRegistrationAdapter.getClientProviders(properties);
        return new DefaultSocialRepository(clientRegistrations, clientProviders);
    }

}
