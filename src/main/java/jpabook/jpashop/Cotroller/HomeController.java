package jpabook.jpashop.Cotroller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j // logger를 쑬수잇다
public class HomeController {


    @RequestMapping("/")
    public String home(){
        log.info("home Controller");
        return "home";
    }

}
