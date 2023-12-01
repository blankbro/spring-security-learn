package io.github.blankbro.securitysessionredis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.web.jackson2.WebServletJackson2Module;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

/**
 * 设置及使用 Redis Session 非活动状态的过期参数 {@link EnableRedisHttpSession#maxInactiveIntervalInSeconds()}
 * <p>
 * Redis Key 在源码中的位置
 * <p>
 * spring:session <br/>
 * {@link RedisIndexedSessionRepository#DEFAULT_NAMESPACE}
 * <p>
 * spring:session:expirations:{expiration} <br/>
 * {@link RedisIndexedSessionRepository#getExpirationsKey(long)}
 * <p>
 * spring:session:sessions:{session_id} <br/>
 * {@link RedisIndexedSessionRepository#getSessionKey(String)}
 * <p>
 * spring:session:sessions:expires:{session_id} <br/>
 * {@link RedisIndexedSessionRepository#getExpiredKey(String)}
 * <p>
 * 1. {@link RedisHttpSessionConfiguration#setImportMetadata(AnnotationMetadata)} 获取非活动状态的过期秒数，
 * 并设置 {@link RedisHttpSessionConfiguration#maxInactiveIntervalInSeconds}
 * <p>
 * 2. {@link RedisHttpSessionConfiguration#sessionRepository()} 将类属性 {@link RedisHttpSessionConfiguration#maxInactiveIntervalInSeconds}
 * 设置到 {@link RedisIndexedSessionRepository#setDefaultMaxInactiveInterval(int)} 中, 并注册为 {@link Bean}
 * <p>
 * 3. {@link RedisIndexedSessionRepository#createSession()} 将 {@link RedisIndexedSessionRepository#defaultMaxInactiveInterval}
 * 设置到 {@link RedisIndexedSessionRepository.RedisSession} 中（创建 Redis Session）
 * <p>
 * 4. 创建、读取、更新 Session: {@link org.springframework.session.web.http.SessionRepositoryFilter.SessionRepositoryRequestWrapper#getSession(boolean)}
 */
@EnableRedisHttpSession
@Configuration
public class SessionRedisConfiguration {

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        template.setKeySerializer(new StringRedisSerializer());

        // @see org.springframework.security.web.jackson2.DefaultSavedRequestMixin
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new WebServletJackson2Module());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));

        template.afterPropertiesSet();

        return template;
    }

    /**
     * Spring {@link javax.servlet.http.HttpSession} 默认 Redis 序列化程序
     * 名称必须为：springSessionDefaultRedisSerializer
     *
     * @param redisTemplate Redis 模板
     * @return Spring {@link javax.servlet.http.HttpSession} 默认 Redis 序列化程序
     * @see RedisHttpSessionConfiguration#setDefaultRedisSerializer(RedisSerializer) 自定义 Spring {@link javax.servlet.http.HttpSession} 默认 Redis 序列化程序
     */
    @Bean
    public RedisSerializer<?> springSessionDefaultRedisSerializer(RedisTemplate<?, ?> redisTemplate) {
        return redisTemplate.getValueSerializer();
    }

}
