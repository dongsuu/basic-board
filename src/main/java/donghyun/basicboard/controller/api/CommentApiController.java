package donghyun.basicboard.controller.api;

import donghyun.basicboard.domain.Comment;
import donghyun.basicboard.domain.Member;
import donghyun.basicboard.domain.Post;
import donghyun.basicboard.dto.CreateCommentDto;
import donghyun.basicboard.login.AuthenticationProvider;
import donghyun.basicboard.service.CommentService;
import donghyun.basicboard.service.MemberService;
import donghyun.basicboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apis/comments")
public class CommentApiController {
    private final CommentService commentService;
    private final PostService postService;
    private final MemberService memberService;

    @PostMapping("/new/{postId}")
    public ResponseEntity<String> createComment(@PathVariable Long postId, @RequestBody CreateCommentDto createCommentDto){
        Long currentMemberId = AuthenticationProvider.getCurrentMemberId();
        Member currentMember = memberService.findById(currentMemberId);
        Post post = postService.findById(postId);

        Comment comment = Comment.createComment(
                currentMember,
                createCommentDto.getContent(),
                post
        );

        commentService.addComment(comment);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId){
        commentService.removeComment(commentId);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/reply/new/{commentId}")
    public ResponseEntity<String> createReply(
            @PathVariable Long commentId,
            @RequestBody CreateCommentDto reply
    ){
        Long currentMemberId = AuthenticationProvider.getCurrentMemberId();
        Member currentMember = memberService.findById(currentMemberId);
        Comment parentComment = commentService.findById(commentId);
        Comment childComment = Comment.createComment(
                currentMember,
                reply.getContent(),
                parentComment.getPost()
        );
        commentService.addReply(parentComment, childComment);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/reply/delete/{commentId}")
    public ResponseEntity<String> deleteReply(@PathVariable Long commentId){
        commentService.removeComment(commentId);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
