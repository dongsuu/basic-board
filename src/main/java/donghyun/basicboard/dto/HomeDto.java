package donghyun.basicboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HomeDto {
    private String name;
    private String email;
    private int age;
    private String nickname;
    private int postCount;
    private int commentCount;
}
