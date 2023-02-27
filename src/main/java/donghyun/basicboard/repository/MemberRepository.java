package donghyun.basicboard.repository;

import donghyun.basicboard.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberRepository {

    private final EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member findById(Long memberId){
        Member findMember = em.find(Member.class, memberId);
        return findMember;
    }

    public Optional<Member> findByEmail(String email){
        log.info("email={}", email);
        String jpql = "select m from Member m where m.email = :email";
        try{
            Member findMember = em.createQuery(jpql, Member.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(findMember);
        } catch(Exception e){
            log.error("존재하지 않는 회원 입니다. error={}", e);
            return Optional.empty();
        }
    }

    public Member findByNickname(String nickname){
        String jpql = "select m from Member m where m.name = :nickname";
        Member findMember = em.createQuery(jpql, Member.class)
                .setParameter("nickname", nickname)
                .getSingleResult();
        return findMember;
    }

    public List<Member> findAll(){
        String jpql = "select m from Member m";
        List<Member> findMembers = em.createQuery(jpql, Member.class)
                .getResultList();
        return findMembers;
    }

    public void remove(Member member){
        em.remove(member);
    }
}
