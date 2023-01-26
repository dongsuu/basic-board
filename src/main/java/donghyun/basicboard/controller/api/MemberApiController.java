package donghyun.basicboard.controller.api;

import donghyun.basicboard.controller.MemberLoginRequestDto;
import donghyun.basicboard.domain.Member;
import donghyun.basicboard.login.TokenInfo;
import donghyun.basicboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Slf4j
@RestController
@Controller
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/members/login")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto){
        String memberId = memberLoginRequestDto.getMemberId();
        String memberPassword = memberLoginRequestDto.getMemberPassword();
        TokenInfo tokenInfo = memberService.loginWithJwt(memberId, memberPassword);
        return tokenInfo;
    }

    @GetMapping("/members/test")
    public String test(){
        return "ok";
    }

    @GetMapping("/members/other")
    public String other(){
        return "ok";
    }

    @PostConstruct
    public void init(){
        Member member = new Member();
        member.createMember("lee", "nickname", 30, "qwe123", "qwe123");
        memberService.join(member);
    }
}
