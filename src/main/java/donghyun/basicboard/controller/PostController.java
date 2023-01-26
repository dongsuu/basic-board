package donghyun.basicboard.controller;

import donghyun.basicboard.controller.file.FileStore;
import donghyun.basicboard.controller.form.CommentForm;
import donghyun.basicboard.controller.form.PostEditForm;
import donghyun.basicboard.controller.form.PostForm;
import donghyun.basicboard.controller.session.SessionConst;
import donghyun.basicboard.domain.*;
import donghyun.basicboard.repository.UploadFileRepository;
import donghyun.basicboard.service.CommentService;
import donghyun.basicboard.service.MemberService;
import donghyun.basicboard.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;
    private final UploadFileRepository uploadFileRepository;

    private final FileStore fileStore;

    @Value("${file.dir}")
    private String fileDir;

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
    public String BoardByName(@PathVariable("boardName") BoardName boardName, Model model){
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
    public String newPostInFreeBoard(@ModelAttribute("postForm") PostForm postForm, HttpServletRequest request) throws IOException {
        Member sessionMember = getSessionMember(request);

        List<UploadFileEntity> uploadFileEntities = fileStore.storeFiles(postForm.getUploadFiles());

        if(sessionMember == null){
            log.error("로그인 하지 않은 사용자 접근입니다. 혹은 세션이 만료되었을 수 있습니다.");
        }

        Member member = memberService.findById(sessionMember.getId());

        postForm.setAuthor(member);

        Post post = new Post();
        post.createPost(postForm.getTitle(), postForm.getAuthor(), BoardName.FREE, postForm.getContent(), uploadFileEntities);
        postService.addPost(post);
        return "redirect:/boards/FREE";
    }

    private Member getSessionMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member sessionMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return sessionMember;
    }

    @ResponseBody
    @GetMapping("/uploadFiles/{fileName}")
    public Resource downloadFiles(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullFilePath(fileName));
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
    public String newPostInSportsBoard(@ModelAttribute("postForm") PostForm postForm, HttpServletRequest request) throws IOException {
        Member sessionMember = getSessionMember(request);

        List<UploadFileEntity> uploadFileEntities = fileStore.storeFiles(postForm.getUploadFiles());

        if(sessionMember == null){
            log.error("로그인 하지 않은 사용자 접근입니다. 혹은 세션이 만료되었을 수 있습니다.");
        }

        Member member = memberService.findById(sessionMember.getId());

        postForm.setAuthor(member);

        Post post = new Post();
        post.createPost(postForm.getTitle(), postForm.getAuthor(), BoardName.SPORTS, postForm.getContent(), uploadFileEntities);
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
    public String newPostInStudyBoard(@ModelAttribute("postForm") PostForm postForm, HttpServletRequest request) throws IOException {
        Member sessionMember = getSessionMember(request);

        List<UploadFileEntity> uploadFileEntities = fileStore.storeFiles(postForm.getUploadFiles());

        if(sessionMember == null){
            log.error("로그인 하지 않은 사용자 접근입니다. 혹은 세션이 만료되었을 수 있습니다.");
        }

        Member member = memberService.findById(sessionMember.getId());

        postForm.setAuthor(member);

        Post post = new Post();
        post.createPost(postForm.getTitle(), postForm.getAuthor(), BoardName.STUDY, postForm.getContent(), uploadFileEntities);
        postService.addPost(post);
        return "redirect:/boards/STUDY";
    }

    /**
     * Detail
     * 게시글 클릭시 댓글을 작성할 수 있는 게시글 세부 페이지로 이동
     */

//    @GetMapping("/boards/{postId}")
//    public String postDetail(@PathVariable("postId") Long postId, Model model, HttpServletRequest request){
//        Post findPost = postService.findById(postId);
//        model.addAttribute("post", findPost);
//
//        Member loginMember = (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
//        Member findMember = memberService.findById(loginMember.getId());
//        model.addAttribute("member", findMember);
//
//        return "boards/posts/detail/" + findPost.getBoardName();
//    }

    @GetMapping("/boards/FREE/{postId}")
    public String FreePostDetail(@PathVariable("postId") Long postId, Model model, HttpServletRequest request){
        Post findPost = postService.findById(postId);
        model.addAttribute("post", findPost);

        Member sessionMember = getSessionMember(request);
        Member findMember = memberService.findById(sessionMember.getId());
        model.addAttribute("member", findMember);
        model.addAttribute("commentForm", new CommentForm());

        List<Comment> findComments = commentService.findAllByPostId(postId);
        model.addAttribute("comments", findComments);

        return "boards/posts/detail/free";
    }

    @GetMapping("/boards/SPORTS/{postId}")
    public String SportsPostDetail(@PathVariable("postId") Long postId, Model model, HttpServletRequest request){
        Post findPost = postService.findById(postId);
        model.addAttribute("post", findPost);

        Member sessionMember = getSessionMember(request);
        Member findMember = memberService.findById(sessionMember.getId());
        model.addAttribute("member", findMember);
        model.addAttribute("commentForm", new CommentForm());

        List<Comment> findComments = commentService.findAllByPostId(postId);
        model.addAttribute("comments", findComments);

        return "boards/posts/detail/sports";
    }

    @GetMapping("/boards/STUDY/{postId}")
    public String StudyPostDetail(@PathVariable("postId") Long postId, Model model, HttpServletRequest request){
        Post findPost = postService.findById(postId);
        model.addAttribute("post", findPost);

        Member sessionMember = getSessionMember(request);
        Member findMember = memberService.findById(sessionMember.getId());
        model.addAttribute("member", findMember);
        model.addAttribute("commentForm", new CommentForm());

        List<Comment> findComments = commentService.findAllByPostId(postId);
        model.addAttribute("comments", findComments);

        return "boards/posts/detail/study";
    }

    /**
     * 게시글 수정
     */
    @GetMapping("/boards/edit/{postId}")
    public String postEditForm(@PathVariable("postId")Long postId, Model model){
        Post editPost = postService.findById(postId);

        PostEditForm postEditForm = new PostEditForm();
        postEditForm.setTitle(editPost.getTitle());
        postEditForm.setBoardName(editPost.getBoardName());
        postEditForm.setContent(editPost.getContent());
        postEditForm.setUploadFiles(editPost.getUploadFiles());


        model.addAttribute("postEditForm", postEditForm);
        return "boards/posts/edit/editForm";
    }

    @PostMapping("/boards/edit/{postId}")
    public String postEdit(@PathVariable("postId") Long postId, @ModelAttribute("postEditForm") PostEditForm postEditForm){
        Post editPost = postService.findById(postId);
        postService.updatePost(postId, postEditForm.getTitle(), postEditForm.getBoardName(), postEditForm.getContent(), postEditForm.getUploadFiles());
        return "redirect:/boards/" + editPost.getBoardName();
    }

    /**
     * 게시글 삭제
     */

    @GetMapping("/boards/delete/{postId}")
    public String postDelete(@PathVariable("postId") Long postId){
        Post removePost = postService.removePost(postId); // 영속성 전이 (CascadeType.REMOVE) -> 연관된 댓글도 함께 여기서 삭제된다.
        return "redirect:/boards/" + removePost.getBoardName();
    }

    /**
     * 게시글 수정 - 첨부파일 제거
     */
    @GetMapping("/boards/uploadFile/edit/{uploadFileId}")
    public String removeUploadFile(@PathVariable("uploadFileId") Long uploadFileId){
        Post findPost = postService.findByUploadFileId(uploadFileId);
        List<UploadFileEntity> uploadFiles = findPost.getUploadFiles();
        System.out.println("uploadFiles = " + uploadFiles.size());
        for (UploadFileEntity uploadFile : uploadFiles) {
            if(uploadFile.getId().equals(uploadFileId)){
                System.out.println("uploadFile = " + uploadFile.getId());
                findPost.removeUploadFile(uploadFile);
                postService.updatePost(findPost.getId(), findPost.getTitle(), findPost.getBoardName(), findPost.getContent(), findPost.getUploadFiles());
                uploadFileRepository.remove(uploadFile);
            }
        }

        return "redirect:/boards/edit/" + findPost.getId();
    }

}
