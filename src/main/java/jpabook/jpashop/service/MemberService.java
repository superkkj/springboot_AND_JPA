package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) //모든 JPA 트랜잭션안에 실행되야되고. 그래야 LAZY 이런걸 쓸  수 있다.
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member); //영속성 때문에 Id값은 디비 들어가기전에도 존재함.
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {

        //EXCEPTION

        List<Member> findMembers = memberRepository.findByName(member.getName()); //실무에선 멀티스레드 환경때문에 DB에 name을 유니크 제약조건을 잡아주자
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    // 회원 전체 조회

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
}
