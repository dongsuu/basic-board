package donghyun.basicboard.controller.form;

import donghyun.basicboard.domain.BoardName;
import donghyun.basicboard.domain.UploadFileEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostEditForm {
    private String title;
    private String content;
    private BoardName boardName;
    private MultipartFile[] uploadFiles;
}
