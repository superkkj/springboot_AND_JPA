package jpabook.jpashop.service;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class) //스프링이랑 엮어서 같이 실행할래 !
@SpringBootTest() //스프링 컨테이너 안에서 테스트를 돌린다.
@Transactional // 커밋안하고 롤백 //테스트 할 땐 롤백할거야~
public class MemberServiceTest {


    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em; //디비로 가는 인서트문 보고싶어

    @Test
//    @Rollback(false)
    public void 회원가입() {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member); //영속성 때문에 커밋 될때 인서트.

        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));

    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예약() throws Exception {

        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2); // 예외 발생해야 함!!

        //then
        fail("예외가 발생해야 한다."); //밑으로 뭔가 로직이 나가버리면? 코드가 돌다 여기로 오면 안돼
    }
}