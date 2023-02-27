package donghyun.basicboard.controller.api;

import donghyun.basicboard.domain.*;
import donghyun.basicboard.dto.PostDto;
import donghyun.basicboard.dto.UpdatePostDto;
import donghyun.basicboard.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostApiController {
    private final PostService postService;
    private final UploadFileService uploadFileService;
    private final S3Upload s3Upload;

    @PostMapping("/new")
    public void createPost(@RequestPart("data") PostDto postDto, @RequestPart("files")MultipartFile[] multipartFiles) throws IOException {
        Member currentMember = AuthenticationProvider.getCurrentMember();

        Post post = Post.createPost(
                postDto.getTitle(),
                currentMember,
                BoardName.valueOf(postDto.getBoardName()),
                postDto.getContent()
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

    }

    @GetMapping("/update/{postId}")
    public UpdatePostDto updatePostGet(@PathVariable Long postId){
        Post findPost = postService.findById(postId);
        List<String> uploadFilePaths = s3Upload.getUploadFiles(findPost);
        return new UpdatePostDto(findPost.getTitle(), findPost.getContent(), findPost.getBoardName().name(), uploadFilePaths);
    }

    @PostMapping("/update/{postId}")
    public void updatePost(@PathVariable Long postId, @RequestPart PostDto postDto, @RequestPart MultipartFile[] multipartFiles) throws IOException {
        postService.updatePost(
                postId,
                postDto.getTitle(),
                BoardName.valueOf(postDto.getBoardName()),
                postDto.getContent(),
                multipartFiles
        );
    }

    @PostMapping("/delete/{postId}")
    public void deletePost(@PathVariable Long postId){
        postService.removePost(postId);
    }
}
