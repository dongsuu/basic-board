package donghyun.basicboard.service;

import donghyun.basicboard.domain.Member;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;

@Getter
@Slf4j
public class UserDetailsImpl extends User {

    private final Member member;

    public UserDetailsImpl(Member member){
        super(member.getEmail(), member.getPassword(), member.getAuthorities());
        this.member = member;
        log.info("member={}", member.toString());
    }
}
