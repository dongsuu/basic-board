package donghyun.basicboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T':HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createDate;
    private List<RepliesDto> replies;
}
