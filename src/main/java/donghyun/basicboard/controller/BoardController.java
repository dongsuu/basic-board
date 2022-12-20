package donghyun.basicboard.controller;

import donghyun.basicboard.controller.session.SessionConst;
import donghyun.basicboard.domain.BoardName;
import donghyun.basicboard.domain.Member;
import donghyun.basicboard.domain.Post;
import donghyun.basicboard.service.BoardService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/boards")
    public String boards(Model model){

        List<BoardName> result = new ArrayList<>();
        result.add(BoardName.FREE);
        result.add(BoardName.SPORTS);
        result.add(BoardName.STUDY);
        model.addAttribute("boards", result);
        return "boards/boards";
    }

    @GetMapping("/boards/{boardName}")
    public String freeBoard(@PathVariable("boardName") BoardName boardName, Model model){
        List<Post> result = postService.findByBoardName(boardName);
        model.addAttribute("posts", result);
        if(boardName.name() == "FREE"){
            return "boards/free";
        }
        else if(boardName.name() == "SPORTS"){
            return "boards/sports";
        }
        else if(boardName.name() == "STUDY"){
            return "boards/study";
        } else{
            return "/boards/boards";
        }
    }

    /**
     * FREE
     */
    @GetMapping("/boards/FREE/new")
    public String newPostInFreeBoardForm(Model model){
        PostForm postForm = new PostForm();
        model.addAttribute("postForm", new PostForm());
        return "boards/posts/free-new";
    }

    @PostMapping("/boards/FREE/new")
    public String newPostInFreeBoard(@ModelAttribute("postForm") PostForm postForm, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if(loginMember == null){
            log.error("로그인 하지 않은 사용자 접근입니다. 혹은 세션이 만료되었을 수 있습니다.");
        }

        Member member = memberService.findById(loginMember.getId());

        postForm.setAuthor(member);

        Post post = new Post();
        post.createPost(postForm.getTitle(), postForm.getAuthor(), BoardName.SPORTS, postForm.getContent());
        postService.addPost(post);
        return "redirect:/boards/FREE";
    }


    /**
     * SPORTS
     */

    @GetMapping("/boards/SPORTS/new")
    public String newPostInSportsBoardForm(Model model){
        PostForm postForm = new PostForm();
        model.addAttribute("postForm", postForm);
        return "boards/posts/sports-new";
    }

    @PostMapping("/boards/SPORTS/new")
    public String newPostInSportsBoard(@ModelAttribute("postForm") PostForm postForm, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if(loginMember == null){
            log.error("로그인 하지 않은 사용자 접근입니다. 혹은 세션이 만료되었을 수 있습니다.");
        }

        Member member = memberService.findById(loginMember.getId());

        postForm.setAuthor(member);

        Post post = new Post();
        post.createPost(postForm.getTitle(), postForm.getAuthor(), BoardName.SPORTS, postForm.getContent());
        postService.addPost(post);
        return "redirect:/boards/SPORTS";
    }

    /**
     * STUDY
     */
    @GetMapping("/boards/STUDY/new")
    public String newPostInSportsStudyForm(Model model){
        PostForm postForm = new PostForm();
        postForm.setBoardName(BoardName.STUDY);
        model.addAttribute("postForm", postForm);
        return "boards/posts/study-new";
    }

    @PostMapping("/boards/STUDY/new")
    public String newPostInStudyBoard(@ModelAttribute("postForm") PostForm postForm, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if(loginMember == null){
            log.error("로그인 하지 않은 사용자 접근입니다. 혹은 세션이 만료되었을 수 있습니다.");
        }

        Member member = memberService.findById(loginMember.getId());

        postForm.setAuthor(member);

        Post post = new Post();
        post.createPost(postForm.getTitle(), postForm.getAuthor(), BoardName.STUDY, postForm.getContent());
        postService.addPost(post);
        return "redirect:/boards/STUDY";
    }

    /**
     * Detail
     * 게시글 클릭시 댓글을 작성할 수 있는 게시글 세부 페이지로 이동
     */

    @GetMapping("/boards/FREE/{postId}")
    public String FreePostDetail(@PathVariable("postId") Long postId, Model model, HttpServletRequest request){
        Post findPost = postService.findById(postId);
        model.addAttribute("post", findPost);

        Member loginMember = (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        Member findMember = memberService.findById(loginMember.getId());
        model.addAttribute("member", findMember);

        return "boards/posts/detail/free";
    }

    @GetMapping("/boards/SPORTS/{postId}")
    public String SportsPostDetail(@PathVariable("postId") Long postId, Model model, HttpServletRequest request){
        Post findPost = postService.findById(postId);
        model.addAttribute("post", findPost);

        Member loginMember = (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        Member findMember = memberService.findById(loginMember.getId());
        model.addAttribute("member", findMember);

        return "boards/posts/detail/sports";
    }

    @GetMapping("/boards/STUDY/{postId}")
    public String StudyPostDetail(@PathVariable("postId") Long postId, Model model, HttpServletRequest request){
        Post findPost = postService.findById(postId);
        model.addAttribute("post", findPost);

        Member loginMember = (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        Member findMember = memberService.findById(loginMember.getId());
        model.addAttribute("member", findMember);

        return "boards/posts/detail/study";
    }

    /**
     * 게시글 수정
     * TODO
     */


    /**
     * 게시글 삭제
     * TODO
     */


}
