package donghyun.basicboard.service;

import donghyun.basicboard.domain.Member;
import donghyun.basicboard.dto.MemberJoinDto;
import donghyun.basicboard.login.JwtTokenProvider;
import donghyun.basicboard.login.TokenInfo;
import donghyun.basicboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Member join(Member member){
        memberRepository.save(member);
        return member;
    }

    public Member login(String id, String password){
        Optional<Member> findMember = memberRepository.findByLoginId(id);
        return findMember.filter(m -> m.getUserPassword().equals(password)).orElse(null);
    }

    public TokenInfo loginWithJwt(String userId, String password){
        // 1. 로그인 id, password기반으로 Authentication 객체 생성
        // 이때, Authentication은 인증 여부를 확인하는 authenticated 값이 false이다.
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);

        // 2. 실제 검증(사용자 비밀번호 체크)이 이뤄지는 부분
        // authenticate 메서드가 실행될 때, CustomUserDetailsService에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증정보를 기반으로 jwt token 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        return tokenInfo;

    }

    /**
     * 회원 수정은 nickname 변경만 가능
     * @param memberId
     * @param newNickname
     */
    @Transactional
    public void updateNickname(Long memberId, String newNickname){
        Member updateMember = memberRepository.findById(memberId);
        updateMember.changeNickname(newNickname);
    }

    public Member findById(Long memberId){
        Member findMember = memberRepository.findById(memberId);
        return findMember;
    }

    public Member findByNickname(String nickname){
        Member findMember = memberRepository.findByNickname(nickname);
        return findMember;
    }

    @Transactional
    public void remove(Long memberId){
        Member removeMember = memberRepository.findById(memberId);
        memberRepository.remove(removeMember);
    }

}
