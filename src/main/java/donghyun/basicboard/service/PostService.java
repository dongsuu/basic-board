package donghyun.basicboard.service;

import donghyun.basicboard.domain.BoardName;
import donghyun.basicboard.domain.Member;
import donghyun.basicboard.domain.Post;
import donghyun.basicboard.domain.UploadFileEntity;
import donghyun.basicboard.repository.CommentRepository;
import donghyun.basicboard.repository.PostRepository;
import donghyun.basicboard.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UploadFileRepository uploadFileRepository;
    private final S3Upload s3Upload;
    private final CommentRepository commentRepository;

    @Transactional
    public Long addPost(Post post){
        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public void updatePost(Long postId, String title, BoardName boardName, String content, MultipartFile[] multipartFiles) throws IOException {
        Post updatePost = postRepository.findById(postId);

        uploadFileRepository.removeAll(updatePost.getUploadFiles());

        List<UploadFileEntity> uploadFileEntities = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            String filePath = s3Upload.upload(multipartFile, updatePost.getId());
            UploadFileEntity uploadFileEntity = UploadFileEntity.createUploadFileEntity(filePath, multipartFile.getOriginalFilename());
            uploadFileEntities.add(uploadFileEntity);
            uploadFileEntity.updatePost(updatePost);
            uploadFileRepository.save(uploadFileEntity);
        }

        updatePost.updatePost(title, boardName, content, uploadFileEntities);
    }

    public List<Post> findByBoardName(BoardName boardName){
        List<Post> result = postRepository.findByBoardName(boardName);
        return result;
    }

    public Post findById(Long postId){
        Post findPost = postRepository.findById(postId);
        return findPost;
    }

    public List<Post> findAll(){
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    @Transactional
    public Post removePost(Long postId){
        Post removePost = postRepository.findById(postId);
        postRepository.remove(removePost);

        return removePost;
    }

    public Post findByUploadFileId(Long uploadFileId){
        Post findPost = postRepository.findByUploadFileId(uploadFileId);
        return findPost;
    }

//    @Transactional
//    public Post createPost(Member author, BoardName boardName, String title, String content) {
//        Post post = new Post();
//        post.createPost(title, author, boardName, content);
//        return post;
//    }
}
