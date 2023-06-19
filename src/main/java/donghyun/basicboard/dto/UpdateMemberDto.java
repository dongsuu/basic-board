package donghyun.basicboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class UpdateMemberDto {
    @NotEmpty(message = "이메일은 필수 속성입니다.")
    private String email;

    @NotEmpty(message = "이름은 필수 속성입니다.")
    private String name;

    private int age;

    @NotEmpty(message = "닉네임은 필수 속성입니다.")
    private String nickname;
}
