package io.github.blankbro.securityoauth2authorizationserver.config;

import com.alibaba.fastjson2.JSON;
import io.github.blankbro.securityoauth2authorizationserver.common.CommonResponseBuilder;
import io.github.blankbro.securityoauth2authorizationserver.common.ResponseCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.sql.DataSource;

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
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        /**
         * 建表语句在 {@link org.springframework.security.core.userdetails.jdbc} 目录下的 users.ddl
         */
        return new JdbcUserDetailsManager(dataSource);
    }

    /**
     * 认证失败的处理器
     *
     * @return
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            log.error("authenticationFailureHandler: {}", exception.getMessage(), exception);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JSON.toJSONString(CommonResponseBuilder.buildByEnum(ResponseCodeEnum.BAD_CREDENTIALS)));
            response.getWriter().flush();
            response.getWriter().close();
        };
    }

    /**
     * 应该也是认证失败的处理器，但是啥时候回走到这里呢？？？
     *
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            log.error("authenticationEntryPoint: {}", authException.getMessage(), authException);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JSON.toJSONString(CommonResponseBuilder.buildErrorResponse(authException.getMessage())));
            response.getWriter().flush();
            response.getWriter().close();
        };
    }
}
