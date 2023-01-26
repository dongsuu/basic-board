package donghyun.basicboard.controller.form;

import donghyun.basicboard.domain.BoardName;
import donghyun.basicboard.domain.UploadFileEntity;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostEditForm {
    private String title;
    private String content;
    private BoardName boardName;
    private List<UploadFileEntity> uploadFiles = new ArrayList<>();
}
