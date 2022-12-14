package donghyun.basicboard.repository;

import donghyun.basicboard.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
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
}
