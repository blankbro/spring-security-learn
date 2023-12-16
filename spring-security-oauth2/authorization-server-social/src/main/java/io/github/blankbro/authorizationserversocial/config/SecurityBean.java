package io.github.blankbro.authorizationserversocial.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Slf4j
@Configuration
public class SecurityBean {

    /**
     * 密码加密者
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 账号密码登录时，认证失败的处理器
     *
     * @return
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            log.error("authenticationFailureHandler: {}", exception.getMessage(), exception);
        };
    }

    /**
     * 应该也是认证失败的处理器，但是啥时候回走到这里呢？？？
     * 发起授权时，如果没有登录会走到该处理器。
     *
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            log.error("authenticationEntryPoint: {}", authException.getMessage(), authException);
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            log.error("accessDeniedHandler: {}", accessDeniedException.getMessage(), accessDeniedException);
        };
    }
}
