package io.github.blankbro.sessionredis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.github.blankbro.sessionredis.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    @Bean
    public RedisTemplate<?,?> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        // Helper类简化了 Redis 数据访问代码
        RedisTemplate<?, ?> template = new RedisTemplate<>();

        // 设置连接工厂。
        template.setConnectionFactory(redisConnectionFactory);

        // 可以使用读写JSON
        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        // ObjectMapper 提供了从基本 POJO（普通旧Java对象）或从通用 JSON 树模型（{@link JsonNode}）读取和写入 JSON 的功能，
        // 以及执行转换的相关功能。
        ObjectMapper objectMapper = new ObjectMapper();

        // 枚举，定义影响Java对象序列化方式的简单开/关功能。
        // 默认情况下启用功能，因此默认情况下日期/时间序列化为时间戳。
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 如果启用，上下文<code> TimeZone </ code>将基本上覆盖任何其他TimeZone信息;如果禁用，则仅在值本身不包含任何TimeZone信息时使用。
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        // 注册使用Jackson核心序列化{@code java.time}对象的功能的类。
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // 添加序列化
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(Constants.DEFAULT_TIME_FORMAT)));

        // 添加反序列化
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(Constants.DEFAULT_TIME_FORMAT)));

        // 用于注册可以扩展该映射器提供的功能的模块的方法; 例如，通过添加自定义序列化程序和反序列化程序的提供程序。
        objectMapper.registerModule(javaTimeModule);

        objectMapper.registerModule(new ParameterNamesModule());

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // 序列化时带全限定名
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 1
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // 2
        template.setKeySerializer(new StringRedisSerializer());

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
