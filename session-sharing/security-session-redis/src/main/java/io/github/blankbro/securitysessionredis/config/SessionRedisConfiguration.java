package io.github.blankbro.securitysessionredis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

/**
 * 设置及使用 Redis Session 非活动状态的过期参数 {@link EnableRedisHttpSession#maxInactiveIntervalInSeconds()}
 * <p>
 * Redis Key 命名规则在源码中的位置
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
 */
@Configuration
@EnableRedisHttpSession
public class SessionRedisConfiguration {

    /**
     * RedisTemplate 的默认序列化器是 JdkSerializationRedisSerializer 但序列化之后的结果极不友好。
     * 所以，此处使用大名鼎鼎的 GenericJackson2JsonRedisSerializer。
     * <p>
     * 但是 {@link org.springframework.security.web.savedrequest.DefaultSavedRequest} 没有无参构造器，
     * 需要通过 {@link org.springframework.security.web.savedrequest.DefaultSavedRequest.Builder} 才能创建。
     * 所以，又需要 objectMapper.registerModules(SecurityJackson2Modules.getModules(getClass().getClassLoader()));
     *
     * @see org.springframework.security.web.jackson2.DefaultSavedRequestMixin
     * @see SecurityJackson2Modules
     * @see RedisHttpSessionConfiguration#setDefaultRedisSerializer(RedisSerializer)
     */
    @Bean
    public RedisSerializer<?> springSessionDefaultRedisSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModules(SecurityJackson2Modules.getModules(getClass().getClassLoader()));
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

}
