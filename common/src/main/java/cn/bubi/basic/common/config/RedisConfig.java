package cn.bubi.basic.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by JoinHan on 2017/8/7.
 */
@Configuration
public class RedisConfig {

    /**
     * 实例化 RedisTemplate 对象
     *
     * @return
     */
    @Bean(value = "redisTemplate")
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.afterPropertiesSet();
        this.setSerializer(redisTemplate);
        return redisTemplate;
    }

    /**
     * 实例化 StringRedisTemplate String
     *
     * @return
     */
    @Bean(value = "stringRedisTemplate")
    public RedisTemplate<String, String> stringRedisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.afterPropertiesSet();
        this.setStringSerializer(redisTemplate);
        return redisTemplate;
    }

    private void setSerializer(RedisTemplate<String, String> template) {

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
    }

    private void setStringSerializer(RedisTemplate<String, String> template) {

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
    }

}
