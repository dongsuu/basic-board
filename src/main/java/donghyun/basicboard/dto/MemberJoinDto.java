package donghyun.basicboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinDto {
    @NotEmpty(message = "이메일은 필수 속성입니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 속성입니다.")
    @Size(min = 6, message = "비밀번호는 최소 6글자 이상이어야 합니다.")
    private String password;

    @NotEmpty(message = "이름은 필수 속성입니다.")
    private String name;

    private int age;

    @NotEmpty(message = "닉네임은 필수 속성입니다.")
    private String nickname;
}
