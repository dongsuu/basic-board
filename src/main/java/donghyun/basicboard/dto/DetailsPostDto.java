package donghyun.basicboard.dto;

import donghyun.basicboard.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DetailsPostDto {
    private Long postId;
    private String title;
    private String content;
    private String author;
    private String authorNickname;
    private String boardName;
    private List<String> uploadFilePaths;
    private LocalDateTime lastModifiedDate;
    private List<ParentCommentDto> parentComments;
}
