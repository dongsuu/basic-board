package donghyun.basicboard.service;

import donghyun.basicboard.domain.Member;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider {

    public static Member getCurrentMember(){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getMember();
    }

    public static Long getCurrentMemberId(){
        return getCurrentMember().getId();
    }
}
