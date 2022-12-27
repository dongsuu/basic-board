package donghyun.basicboard.controller.form;

import donghyun.basicboard.domain.BoardName;
import lombok.Data;

@Data
public class PostEditForm {
    private String title;
    private String content;
    private BoardName boardName;
}
