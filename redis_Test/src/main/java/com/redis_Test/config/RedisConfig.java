package com.redis_Test.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
// @EnableCaching - 캐시 기능을 사용하려할 때 붙여줍니다.
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
    	// LettuceConnectionFactory를 이용해 Redis 서버에 대한 연결 설정
    	// JedisConnectionFactory를 이용해 연결도 가능합니다.
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public CacheManager cacheManager() {
//    	RedisCacheManagerBuilder는 RedisCacheManager 인스턴스를 생성하는 데 사용됩니다.
//    	redisConnectionFactory() 빈에서 얻은 RedisConnectionFactory로 초기화됩니다.
        RedisCacheManager.RedisCacheManagerBuilder builder =
                RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory());

//		RedisCacheConfiguration은 Redis 캐시의 동작을 구성하는 데 사용됩니다.
//      .serializeValuesWith()는 캐시 값의 직렬화를 구성합니다. 
//      GenericJackson2JsonRedisSerializer은 Jackson을 사용하여 캐시 값을 JSON으로 직렬화합니다.
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())) // Value Serializer 변경
				.disableCachingNullValues()	//
                .entryTtl(Duration.ofMinutes(30L));

        builder.cacheDefaults(configuration);

        return builder.build();
//        .entryTtl(Duration.ofMinutes(30L))은 캐시 항목의 TTL(Time-To-Live)을 설정합니다. 캐시 항목은 30분 후에 만료됩니다.
//
//        builder.cacheDefaults(configuration) 메소드는 이 캐시 관리자가 생성한 Redis 캐시의 기본 구성을 설정합니다.
//        마지막으로 builder.build()는 제공된 설정으로 구성된 RedisCacheManager 인스턴스를 빌드하고 반환합니다.
    }
}