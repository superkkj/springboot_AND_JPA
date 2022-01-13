package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //    @PersistenceContext // jPA 엔티티 매니저를 스프링 엔티티매니저에 주입해줌.
    private final EntityManager em; // 스프링이 엔티티 매니저 만들어서 주입


    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() { //sql 과는 기능적으론 같다 차이는 ? : 엔티티 객체를 대상으로 조
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();// 두번째 인자는 반환 형식

    }


    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
