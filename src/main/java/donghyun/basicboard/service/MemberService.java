package donghyun.basicboard.service;

import donghyun.basicboard.domain.Member;
import donghyun.basicboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member){
        memberRepository.save(member);
        return member.getId();
    }

    public Member login(String id, String password){
        Optional<Member> findMember = memberRepository.findByLoginId(id);
        return findMember.filter(m -> m.getUserPassword().equals(password)).orElse(null);
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
