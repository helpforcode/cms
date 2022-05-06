package com.example.cms.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.StringUtils;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.password}")
    private String pwd;

    // @Bean
    // public LettuceConnectionFactory lettuceConnectionFactory() {
    //     // standalone via lettuce
    //     RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
    //     redisConfiguration.setPassword("123456");
    //     return new LettuceConnectionFactory(redisConfiguration);
    // }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost");
        if (StringUtils.hasLength(pwd)) {
            redisStandaloneConfiguration.setPassword(pwd);
        }
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    /**
     * redis cache 序列化
     * @return
     */
    // @Bean
    // public RedisCacheConfiguration redisCacheConfiguration() {
    //     return RedisCacheConfiguration.defaultCacheConfig()
    //             .serializeValuesWith(RedisSerializationContext.SerializationPair
    //                     .fromSerializer(new GenericJackson2JsonRedisSerializer()));
    // }
}
