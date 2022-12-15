package donghyun.basicboard.service;

import donghyun.basicboard.domain.Board;
import donghyun.basicboard.domain.Post;
import donghyun.basicboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long addPost(Post post){
        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public void updatePost(Long postId, String title, Board board, String content){
        Post updatePost = postRepository.findById(postId);
        updatePost.updatePost(title, board, content);
    }

    public List<Post> findAll(){
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    @Transactional
    public void removePost(Long postId){
        Post removePost = postRepository.findById(postId);
        postRepository.remove(removePost);
    }
}
