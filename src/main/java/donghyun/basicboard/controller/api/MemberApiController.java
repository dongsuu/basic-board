package donghyun.basicboard.controller.api;

import donghyun.basicboard.domain.BoardName;
import donghyun.basicboard.domain.Post;
import donghyun.basicboard.dto.MemberJoinDto;
import donghyun.basicboard.dto.MemberLoginRequestDto;
import donghyun.basicboard.domain.Member;
import donghyun.basicboard.login.TokenInfo;
import donghyun.basicboard.service.MemberService;
import donghyun.basicboard.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/apis/members")
public class MemberApiController {
    private final MemberService memberService;
    private final PostService postService;

    @PostMapping("/join")
    public ResponseEntity<MemberJoinDto> join(@Valid @RequestBody MemberJoinDto memberJoinDto){
        Member member = Member.createMember(
                memberJoinDto.getName(),
                memberJoinDto.getNickname(),
                memberJoinDto.getAge(),
                memberJoinDto.getEmail(),
                memberJoinDto.getPassword()
        );
        memberService.join(member);
        return new ResponseEntity<>(memberJoinDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto){
        String email = memberLoginRequestDto.getEmail();
        String password = memberLoginRequestDto.getPassword();
        TokenInfo tokenInfo = memberService.loginWithJwt(email, password);
        return tokenInfo;
    }

    @GetMapping("/test")
    public String test(){
        return "ok";
    }

    @GetMapping("/other")
    public String other(){
        return "ok";
    }

    @PostConstruct
    public void init(){
        Member member = Member.createMember("lee", "nickname", 30, "qwe123", "qwe123");
        memberService.join(member);
    }
}
