package donghyun.basicboard.domain;

import lombok.RequiredArgsConstructor;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@RequiredArgsConstructor
public abstract class DateEntity {
    private final LocalDateTime createDate;
    private final LocalDateTime lastModifiedDate;
}
