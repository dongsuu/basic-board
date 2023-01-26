package donghyun.basicboard.domain;

import lombok.Builder;
import lombok.Getter;
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
public class Member extends DateEntity implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public Member() {
    }

    public void createMember(String name, String nickname, int age, String userId, String userPassword){
        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.userId = userId;
        this.userPassword = userPassword;
        this.roles.add("USER");
    }

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
