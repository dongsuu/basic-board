package donghyun.basicboard.dto;

import donghyun.basicboard.domain.BoardName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostDto {
    private String title;
    private String content;
    private BoardName boardName;
}
