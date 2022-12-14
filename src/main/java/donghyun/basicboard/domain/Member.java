package donghyun.basicboard.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Getter
public class Member extends DateEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty(message = "이름은 필수 속성입니다.")
    private String name;
    @NotEmpty(message = "닉네임은 필수 속성입니다.")
    private String nickname;
    private int age;
    @NotEmpty(message = "아이디는 필수 속성입니다.")
    private String userId;
    @NotEmpty(message = "비밀번호는 필수 속성입니다.")
    private String userPassword;

    public Member() {
    }

    public void createMember(String name, String nickname, int age, String userId, String userPassword){
        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.userId = userId;
        this.userPassword = userPassword;

    }

}
