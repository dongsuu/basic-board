package donghyun.basicboard.repository;

import donghyun.basicboard.domain.RefreshToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RefreshTokenRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisTemplate<String, String> redisBlackListTemplate;

    RefreshTokenRepository(
            RedisTemplate<String, String> redisTemplate,
            @Qualifier("redisBlackListTemplate") RedisTemplate<String, String> redisBlackListTemplate
    ) {
        this.redisTemplate = redisTemplate;
        this.redisBlackListTemplate = redisBlackListTemplate;
    }

    public void save(RefreshToken refreshToken){
        log.info("refreshToken email = {}", refreshToken.getEmail());
        log.info("refreshToken string = {}", refreshToken.getRefreshToken());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(String.class));
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken.getEmail(), refreshToken.getRefreshToken());
        redisTemplate.expire(refreshToken.getEmail(), 600000L, TimeUnit.SECONDS);
    }

    public Optional<RefreshToken> findByEmail(String email){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String refreshToken = valueOperations.get(email);
        log.info("refreshToken findByEmail = {}", refreshToken);

        if(Objects.isNull(refreshToken)){
            return Optional.empty();
        }

        return Optional.of(new RefreshToken(email, refreshToken));
    }

    public void setBlackList(String email, String accessToken, Object o){
        log.info("refreshToken email = {}", email);
        log.info("refreshToken string = {}", accessToken);
        redisBlackListTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
        ValueOperations<String, String> valueOperations = redisBlackListTemplate.opsForValue();
        valueOperations.set(accessToken, o.toString(), 6400, TimeUnit.SECONDS);
    }

    public Object getBlackList(String key){
        return redisBlackListTemplate.opsForValue().get(key);
    }

    public boolean hasKeyBlackList(String key){
        return redisBlackListTemplate.hasKey(key);
    }

    public void remove(RefreshToken refreshToken){
        redisTemplate.delete(refreshToken.getEmail());
    }


}
