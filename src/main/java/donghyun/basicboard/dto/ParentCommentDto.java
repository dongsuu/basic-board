package donghyun.basicboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParentCommentDto {
    private Long commentId;
    private String authorNickname;
    private String content;
    private LocalDateTime createDate;
    private List<RepliesDto> replies;
}
