package donghyun.basicboard.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends DateEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String nickname;
    private int age;

    private String email;
    private String password;

    @OneToMany(mappedBy = "author")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Comment> comments = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    public static Member createMember(String name, String nickname, int age, String email, String password){
        Member member = new Member();
        member.name = name;
        member.nickname = nickname;
        member.age = age;
        member.email = email;
        member.password = password;
        member.roles.add("USER");
        return member;
    }

    public void updateMember(String name, String nickname, int age, String email){
        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.email = email;
    }

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

}
