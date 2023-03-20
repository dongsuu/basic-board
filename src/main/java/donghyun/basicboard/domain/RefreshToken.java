package donghyun.basicboard.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@AllArgsConstructor
public class RefreshToken {
    @Id
    private String email;
    private String refreshToken;
}
