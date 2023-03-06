package donghyun.basicboard.service;

import donghyun.basicboard.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationProvider {

    public static Member getCurrentMember(){
        log.info("userDetails={}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userDetails.getMember();
    }

    public static Long getCurrentMemberId(){
        return getCurrentMember().getId();
    }
}
