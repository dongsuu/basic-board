package donghyun.basicboard.controller;

import donghyun.basicboard.controller.session.SessionConst;
import donghyun.basicboard.domain.Comment;
import donghyun.basicboard.domain.Member;
import donghyun.basicboard.domain.Post;
import donghyun.basicboard.service.CommentService;
import donghyun.basicboard.service.MemberService;
import donghyun.basicboard.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final PostService postService;


    @PostMapping("/comments/new/{postId}")
    public String createComment(@PathVariable("postId") Long postId, @ModelAttribute("commentForm") CommentForm commentForm, Model model, HttpServletRequest request){
        Member loginMember = (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        Member commentMember = memberService.findById(loginMember.getId());
        Post findPost = postService.findById(postId);
        Comment comment = new Comment();
        comment.createComment(commentMember, commentForm.getContent(), findPost);
        commentService.addComment(comment);

        return "redirect:/boards/" + findPost.getBoardName() + "/" + findPost.getId();
    }
}
