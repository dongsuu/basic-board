package donghyun.basicboard.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "아이디는 필수 속성입니다.")
    private String id;

    @NotEmpty(message = "비밀번호는 필수 속성입니다.")
    @Size(min = 6, message = "비밀번호는 최소 6글자 이상이어야 합니다.")
    private String password;

    @NotEmpty(message = "이름은 필수 속성입니다.")
    private String name;

    private int age;

    @NotEmpty(message = "닉네임은 필수 속성입니다.")
    private String nickname;
}
