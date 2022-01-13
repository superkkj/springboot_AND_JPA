package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {


    @GetMapping("/hello")
    public String hello(Model model) { // 데이터를 뷰로 넘긴다~
        model.addAttribute("data", "Heloo!!!!");
        return "hello"; // 화면 잉름.
    }
}