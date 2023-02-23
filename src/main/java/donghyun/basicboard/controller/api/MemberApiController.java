package donghyun.basicboard.controller.api;

import donghyun.basicboard.dto.MemberJoinDto;
import donghyun.basicboard.dto.MemberLoginRequestDto;
import donghyun.basicboard.domain.Member;
import donghyun.basicboard.login.TokenInfo;
import donghyun.basicboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@Slf4j
@RestController
@Controller
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("members/join")
    public ResponseEntity<MemberJoinDto> join(@Valid @RequestBody MemberJoinDto memberJoinDto){
        Member member = Member.createMember(
                memberJoinDto.getName(),
                memberJoinDto.getNickname(),
                memberJoinDto.getAge(),
                memberJoinDto.getUserId(),
                memberJoinDto.getPassword()
        );
        memberService.join(member);
        return new ResponseEntity<>(memberJoinDto, HttpStatus.OK);
    }

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
        Member member = Member.createMember("lee", "nickname", 30, "qwe123", "qwe123");
        memberService.join(member);
    }
}
