//package com.example.oauth2.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.*;
//
//import java.time.Duration;
//import java.util.HashMap;
//import java.util.Map;
//
//
//@Configuration
//@EnableCaching
//public class CacheConfig extends CachingConfigurerSupport {
//    @Autowired
//    private LettuceConnectionFactory lettuceConnectionFactory;
//
//    //自定义的缓存key的生成策略
////    @Bean
////    public KeyGenerator keyGenerator() {
////        return new KeyGenerator(){
////            @Override
////            public Object generate(Object target, Method method, Object... params) {
////                StringBuffer sb = new StringBuffer();
////                sb.append(target.getClass().getName());
////                sb.append('.');
////                sb.append(method.getName());
////                for(Object obj:params){
////                    sb.append(obj.toString());
////                }
////                return sb.toString();
////            }
////        };
////    }
//
//    //自定义缓存配置
//    private RedisCacheConfiguration getRedisCacheConfiguration(Duration duration) {
//        //获取默认配置
//        RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
//        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
//        return defaultCacheConfiguration
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer))
//                .entryTtl(duration);
//    }
//
//    //缓存管理器
//    @Bean
//    public RedisCacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
//        Map<String, RedisCacheConfiguration> initialCacheConfigurations = new HashMap<>();
//        //获取默认配置
//        RedisCacheConfiguration defaultCacheConfiguration = getRedisCacheConfiguration(Duration.ZERO);
//        //当redis注解value为“10min”时候，采用下面这个配置
//        initialCacheConfigurations.put("10min", getRedisCacheConfiguration(Duration.ofMinutes(10L)));
//        initialCacheConfigurations.put("1h", getRedisCacheConfiguration(Duration.ofHours(1L)));
//        initialCacheConfigurations.put("6h", getRedisCacheConfiguration(Duration.ofHours(6L)));
//        RedisCacheManager redisCacheManager = RedisCacheManager.builder(lettuceConnectionFactory)
//                .cacheDefaults(defaultCacheConfiguration)
//                .withInitialCacheConfigurations(initialCacheConfigurations)
//                .transactionAware()
//                .build();
//        return redisCacheManager;
//    }
//
//    //设置RedisTemplate序列化
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory ) {
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
//        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
//        RedisSerializer stringSerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(stringSerializer);//key序列化
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);//value序列化
//        redisTemplate.setHashKeySerializer(stringSerializer);//Hash key序列化
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);//Hash value序列化
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//}