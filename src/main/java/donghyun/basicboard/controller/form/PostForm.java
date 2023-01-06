package donghyun.basicboard.controller.form;

import donghyun.basicboard.domain.BoardName;
import donghyun.basicboard.domain.Member;
import donghyun.basicboard.domain.UploadFileEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class PostForm {
    private String title;
    private String content;
    private Member author;
    private BoardName boardName;
    private List<MultipartFile> uploadFiles;

    public PostForm() {
    }
}
