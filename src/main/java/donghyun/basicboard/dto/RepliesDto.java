package donghyun.basicboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RepliesDto {
    private Long commentId;
    private String replyAuthorNickname;
    private String content;
    private LocalDateTime createDate;
}
