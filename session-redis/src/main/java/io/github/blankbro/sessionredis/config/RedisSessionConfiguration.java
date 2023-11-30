package io.github.blankbro.sessionredis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

/**
 * 设置及使用 Redis Session 非活动状态的过期参数 {@link EnableRedisHttpSession#maxInactiveIntervalInSeconds()}
 * <p>
 * Redis Key 在源码中的位置
 * <p>
 * spring:session <br/>
 * {@link org.springframework.session.data.redis.RedisIndexedSessionRepository#DEFAULT_NAMESPACE}
 * <p>
 * spring:session:expirations:{expiration} <br/>
 * {@link org.springframework.session.data.redis.RedisIndexedSessionRepository#getExpirationsKey(long)}
 * <p>
 * spring:session:sessions:{session_id} <br/>
 * {@link org.springframework.session.data.redis.RedisIndexedSessionRepository#getSessionKey(String)}
 * <p>
 * spring:session:sessions:expires:{session_id} <br/>
 * {@link org.springframework.session.data.redis.RedisIndexedSessionRepository#getExpiredKey(String)}
 * <p>
 * 1. {@link org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration#setImportMetadata(AnnotationMetadata)} 获取非活动状态的过期秒数，
 * 并设置 {@link org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration#maxInactiveIntervalInSeconds}
 * <p>
 * 2. {@link RedisHttpSessionConfiguration#sessionRepository()} 将类属性 {@link RedisHttpSessionConfiguration#maxInactiveIntervalInSeconds}
 * 设置到 {@link org.springframework.session.data.redis.RedisIndexedSessionRepository#setDefaultMaxInactiveInterval(int)} 中, 并注册为 {@link Bean}
 * <p>
 * 3. {@link RedisIndexedSessionRepository#createSession()} 将 {@link RedisIndexedSessionRepository#defaultMaxInactiveInterval}
 * 设置到 {@link RedisIndexedSessionRepository.RedisSession} 中（创建 Redis Session）
 * <p>
 * 4. 创建、读取、更新 Session: {@link org.springframework.session.web.http.SessionRepositoryFilter.SessionRepositoryRequestWrapper#getSession(boolean)}
 */
@EnableRedisHttpSession
@Configuration
public class RedisSessionConfiguration {


}
