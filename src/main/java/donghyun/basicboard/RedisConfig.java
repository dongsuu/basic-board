package donghyun.basicboard;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    private final String redisHost;
    private final int redisPort;

    public RedisConfig(
        @Value(value = "${spring.redis.host}") String redisHost,
        @Value(value = "${spring.redis.port}") int redisPort
    ){
        this.redisHost = redisHost;
        this.redisPort = redisPort;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        return new LettuceConnectionFactory(redisHost, redisPort);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate(){
//        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    @Bean @Qualifier("redisBlackListTemplate")
    public RedisTemplate<?, ?> redisBlackListTemplate(){
//        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
        RedisTemplate<String, String> redisBlackListTemplate = new RedisTemplate<>();
        redisBlackListTemplate.setKeySerializer(new StringRedisSerializer());
        redisBlackListTemplate.setValueSerializer(new StringRedisSerializer());
        redisBlackListTemplate.setConnectionFactory(redisConnectionFactory());
        return redisBlackListTemplate;
    }
}
