package donghyun.basicboard.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "author")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Comment> comments = new ArrayList<>();

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
