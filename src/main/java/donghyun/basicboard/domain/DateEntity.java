package donghyun.basicboard.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class DateEntity {
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;

    @PrePersist
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        createDate = now;
        lastModifiedDate = now;
    }

    @PreUpdate
    public void preUpdate(){
        LocalDateTime now = LocalDateTime.now();
        lastModifiedDate = now;
    }
}
