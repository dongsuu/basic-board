package donghyun.basicboard.controller.api;

import donghyun.basicboard.domain.Member;
import donghyun.basicboard.dto.HomeDto;
import donghyun.basicboard.login.AuthenticationProvider;
import donghyun.basicboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apis")
@Slf4j
public class HomeApiController {
    private final MemberService memberService;

    @GetMapping("/")
    public HomeDto home(){
        Long currentMemberId = AuthenticationProvider.getCurrentMemberId();
        log.info("currentMemberId={}", currentMemberId);
        Member currentMember = memberService.findById(currentMemberId);
        log.info("currentMember={}", currentMember);
        return new HomeDto(
                currentMember.getName(),
                currentMember.getEmail(),
                currentMember.getAge(),
                currentMember.getNickname(),
                currentMember.getPosts().size(),
                currentMember.getComments().size()
        );
    }

}
