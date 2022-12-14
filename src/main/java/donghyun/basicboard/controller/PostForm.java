package donghyun.basicboard.controller;

import donghyun.basicboard.domain.BoardName;
import donghyun.basicboard.domain.Member;
import donghyun.basicboard.domain.UploadFileEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class PostForm {
    private String title;
    private String content;
    private Member author;
    private BoardName boardName;
    private List<UploadFileEntity> uploadFiles;

    public PostForm() {
    }
}
