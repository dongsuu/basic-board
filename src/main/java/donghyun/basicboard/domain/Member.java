package donghyun.basicboard.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Getter
public class Member extends DateEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String nickname;
    private int age;

    private String userId;
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

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

}
