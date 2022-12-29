package donghyun.basicboard.controller;

import donghyun.basicboard.controller.form.CommentForm;
import donghyun.basicboard.controller.session.SessionConst;
import donghyun.basicboard.domain.BoardName;
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
import java.util.List;

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

    @GetMapping("/comments/reply/new/{commentId}")
    public String createReplyForm(@PathVariable("commentId") Long commentId, Model model, HttpServletRequest request){
        Member member = (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        Comment superComment = commentService.findById(commentId);
        BoardName boardName = superComment.getPost().getBoardName();
        Post post = superComment.getPost();
        List<Comment> comments = commentService.findAllByPostId(post.getId());
        model.addAttribute("reply", true);
        model.addAttribute("post", post);
        model.addAttribute("member", member);
        model.addAttribute("commentForm", new CommentForm());
        model.addAttribute("comment", superComment);
        model.addAttribute("comments", comments);

        String boardNameToken = null;
        if(boardName == BoardName.FREE){
            boardNameToken = "free";
        } else if(boardName == BoardName.SPORTS){
            boardNameToken = "sports";
        } else if(boardName == BoardName.STUDY){
            boardNameToken = "study";
        }

        return "boards/posts/detail/" + boardNameToken;
    }

    @PostMapping("/comments/reply/new/{commentId}")
    public String createReply(@PathVariable("commentId") Long commentId, @ModelAttribute("commentForm") CommentForm commentForm, HttpServletRequest request){
        Member member = (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        Member findMember = memberService.findById(member.getId());
        Comment parentComment = commentService.findById(commentId);
        Comment reply = new Comment();
        reply.createComment(findMember, commentForm.getContent(), parentComment.getPost());
        commentService.addReply(parentComment, reply);
        return "redirect:/boards/" + parentComment.getPost().getBoardName() + "/" + parentComment.getPost().getId();
    }


    /**
     * 댓글 삭제
     */
    @GetMapping("/comments/delete/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId){
        Comment comment = commentService.findById(commentId);
        commentService.removeComment(commentId);
        return "redirect:/boards/" +  comment.getPost().getBoardName() + "/" + comment.getPost().getId();
    }

    /**
     * 대댓글 삭제
     */
    @GetMapping("/comments/reply/delete/{replyId}")
    public String deleteReply(@PathVariable("replyId") Long commentId){
        Comment comment = commentService.findById(commentId);
        commentService.removeComment(commentId);
        return "redirect:/boards/" +  comment.getPost().getBoardName() + "/" + comment.getPost().getId();
    }


}
