package donghyun.basicboard.controller.api;

import donghyun.basicboard.domain.*;
import donghyun.basicboard.dto.CreatePostDto;
import donghyun.basicboard.dto.PostDto;
import donghyun.basicboard.dto.UpdatePostDto;
import donghyun.basicboard.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apis/posts")
@Slf4j
public class PostApiController {
    private final PostService postService;
    private final UploadFileService uploadFileService;
    private final S3Upload s3Upload;

    @GetMapping("/{boardName}")
    public ResponseEntity<List<PostDto>> findPostsByBoardName(@PathVariable BoardName boardName){
        log.info("GET POSTS REQUEST");
        List<PostDto> result = postService.findByBoardName(boardName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/upload/new")
    public ResponseEntity<String> createPostWithImages(@RequestPart("data") CreatePostDto createPostDto, @RequestPart("files")MultipartFile[] multipartFiles) throws IOException {
        Member currentMember = AuthenticationProvider.getCurrentMember();

        Post post = Post.createPost(
                createPostDto.getTitle(),
                currentMember,
                createPostDto.getBoardName(),
                createPostDto.getContent()
        );
        postService.addPost(post);

        if(multipartFiles.length != 0){
            for (MultipartFile multipartFile : multipartFiles) {
                String s3FilePath = s3Upload.upload(multipartFile, post.getId());
                UploadFileEntity uploadFileEntity = UploadFileEntity.createUploadFileEntity(s3FilePath, multipartFile.getOriginalFilename());
                uploadFileEntity.updatePost(post);
                uploadFileService.save(uploadFileEntity);
            }
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> createPost(@RequestPart("data") CreatePostDto createPostDto) throws IOException {
        Member currentMember = AuthenticationProvider.getCurrentMember();

        Post post = Post.createPost(
                createPostDto.getTitle(),
                currentMember,
                createPostDto.getBoardName(),
                createPostDto.getContent()
        );
        postService.addPost(post);

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/details/{postId}")
    public UpdatePostDto postDetails(@PathVariable Long postId){
        Post findPost = postService.findById(postId);
        List<String> uploadFilePaths = s3Upload.getUploadFiles(findPost);
        log.info("uploadFilePathsSize = {}", uploadFilePaths.size());
        return new UpdatePostDto(
                findPost.getTitle(),
                findPost.getContent(),
                findPost.getBoardName().name(),
                uploadFilePaths,
                findPost.getAuthor().getName(),
                findPost.getLastModifiedDate());
    }

    @GetMapping("/update/{postId}")
    public UpdatePostDto updatePostGet(@PathVariable Long postId){
        Post findPost = postService.findById(postId);
        List<String> uploadFilePaths = s3Upload.getUploadFiles(findPost);
        return new UpdatePostDto(
                findPost.getTitle(),
                findPost.getContent(),
                findPost.getBoardName().name(),
                uploadFilePaths,
                findPost.getAuthor().getName(),
                findPost.getLastModifiedDate()
        );
    }

    @PostMapping("/update/upload/{postId}")
    public void updatePostWithImages(@PathVariable Long postId, @RequestPart CreatePostDto createPostDto, @RequestPart MultipartFile[] multipartFiles) throws IOException {
        postService.updatePostWithImages(
                postId,
                createPostDto.getTitle(),
                createPostDto.getBoardName(),
                createPostDto.getContent(),
                multipartFiles
        );
    }

    @PostMapping("/update/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable Long postId, @RequestBody CreatePostDto createPostDto) {
        postService.updatePost(
                postId,
                createPostDto.getTitle(),
                createPostDto.getBoardName(),
                createPostDto.getContent()
        );
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/delete/{postId}")
    public void deletePost(@PathVariable Long postId){
        postService.removePost(postId);
    }

}
