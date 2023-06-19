package donghyun.basicboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MyInfoDto {
    private String name;
    private String email;
    private int age;
    private String nickname;
    private List<PostDto> posts;
    private List<CreateCommentDto> comments;
}
