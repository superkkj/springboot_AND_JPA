package jpabook.jpashop.Cotroller;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/members/new")
    public String createForm(Model model) { // date를 실어서 넘기는 model
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";

    }


    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) { // 오류가 담겨서 실행됨

        if (result.hasErrors()) {
            return "members/createMemberForm"; // 스프링이 바인딩 리절트를 같이 보내준다 이화면으로
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);
        memberService.join(member);

        return "redirect:/";

    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
