package donghyun.basicboard.controller.api;

import donghyun.basicboard.dto.MemberJoinDto;
import donghyun.basicboard.dto.MemberLoginRequestDto;
import donghyun.basicboard.domain.Member;
import donghyun.basicboard.dto.MyInfoDto;
import donghyun.basicboard.dto.UpdateMemberDto;
import donghyun.basicboard.login.TokenInfo;
import donghyun.basicboard.service.MemberService;
import donghyun.basicboard.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
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
        log.info("login request start");
        String email = memberLoginRequestDto.getEmail();
        String password = memberLoginRequestDto.getPassword();
        TokenInfo tokenInfo = memberService.loginWithJwt(email, password);
        return tokenInfo;
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String accessTokenWithType){
        memberService.logout(accessTokenWithType);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/myInfo")
    public ResponseEntity<MyInfoDto> myInfo(){
        MyInfoDto myInfo = memberService.myInfo();
        return new ResponseEntity<>(myInfo, HttpStatus.OK);
    }

    @PostMapping("/update/myInfo")
    public ResponseEntity<String> updateMember(@RequestBody UpdateMemberDto updateMemberDto){
        memberService.updateMember(updateMemberDto);
        return new ResponseEntity<>("OK", HttpStatus.OK);
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
