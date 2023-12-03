package io.github.blankbro.securitycustom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 为 SpringBoot 开启跨域请求。
 * SpringSecurity 开启跨域需要单独配置，通过 http.cors(); 开启
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOrigins("*")
                // 是否允许 cookie
                .allowCredentials(true)
                // 设置允许的请求方式
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.PUT.name()
                )
                // 设置允许的 header 属性
                .allowedHeaders("*")
                // 跨域允许时间
                .maxAge(3600);
    }
}
