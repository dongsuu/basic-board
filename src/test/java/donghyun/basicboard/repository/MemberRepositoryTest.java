package donghyun.basicboard.repository;

import donghyun.basicboard.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    public void member_save(){
        //given
        Member member = new Member();
        member.createMember("memberA", "memA", 20, "id", "pw");


        //when
        Long savedId = memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isEqualTo(member);
        assertThat(savedId).isEqualTo(member.getId());

    }

    @Test
    public void member_findAll(){
        //given
        Member member1 = new Member();
        member1.createMember("memberA", "memA", 20, "id", "pw");

        Member member2 = new Member();
        member2.createMember("memberB", "memB", 30, "idB", "pwB");

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<Member> findMembers = memberRepository.findAll();

        //then
        assertThat(findMembers.size()).isEqualTo(2);
    }

}