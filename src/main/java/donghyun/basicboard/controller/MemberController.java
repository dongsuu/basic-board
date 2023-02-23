package donghyun.basicboard.controller;

import donghyun.basicboard.controller.form.LoginForm;
import donghyun.basicboard.controller.form.MemberForm;
import donghyun.basicboard.controller.session.SessionConst;
import donghyun.basicboard.domain.Comment;
import donghyun.basicboard.domain.Member;
import donghyun.basicboard.domain.Post;
import donghyun.basicboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String joinForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/join";
    }

    @PostMapping("/members/new")
    public String join(@Valid  @ModelAttribute("memberForm") MemberForm memberForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "members/join";
        }

        Member member = Member.createMember(memberForm.getName(), memberForm.getNickname(), memberForm.getAge(), memberForm.getId(), memberForm.getPassword());
        memberService.join(member);

        log.info("===== 회원가입 완료 =====");
        return "redirect:/";
    }

    @GetMapping("/members/login")
    public String loginForm(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "members/login";
    }

//    @PostMapping("/members/login")
//    public String login(@ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request){
//        Member loginMember = memberService.login(loginForm.getId(), loginForm.getPassword());
//        log.info("loginMember={}", loginMember);
//
//        // 로그인 실패
//        if(loginMember == null){
//            log.info("로그인 실패");
//        }
//
//        // 로그인 성공
//        HttpSession session = request.getSession();
//        session.setAttribute("loginMember", loginMember);
//
//        return "redirect:/";
//    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:/";
    }


    /**
     * 내 정보
     */
    @GetMapping("/myInfo")
    public String myInfo(HttpServletRequest request, Model model){
        Member sessionMember = getSessionMember(request);

        Member member = memberService.findById(sessionMember.getId());

        List<Post> posts = member.getPosts();
        List<Comment> comments = member.getComments();
        model.addAttribute("member", member);
        model.addAttribute("postsSize", posts.size());
        model.addAttribute("commentsSize", comments.size());

        return "members/myInfo";
    }

    private Member getSessionMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member sessionMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return sessionMember;
    }


    /**
     * Join Validation
     */
    @GetMapping("/validation/id/{joinId}")
    public String validateId(@RequestParam(value = "joinId") String joinId){
        log.info("joinId={}", joinId);

        return "members/join";
    }
}
