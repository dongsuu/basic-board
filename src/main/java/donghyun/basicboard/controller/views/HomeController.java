package donghyun.basicboard.controller.views;

import donghyun.basicboard.controller.session.SessionConst;
import donghyun.basicboard.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model){

        // 세션이 비어있을 때. (세션 타임아웃)
        if(loginMember == null){
            return "home";
        }

        model.addAttribute("member", loginMember);

        return "login-home";
    }
}
