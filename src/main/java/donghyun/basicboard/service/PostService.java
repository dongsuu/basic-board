package donghyun.basicboard.service;

import donghyun.basicboard.domain.BoardName;
import donghyun.basicboard.domain.Member;
import donghyun.basicboard.domain.Post;
import donghyun.basicboard.repository.CommentRepository;
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
    private final CommentRepository commentRepository;

    @Transactional
    public Long addPost(Post post){
        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public void updatePost(Long postId, String title, BoardName boardName, String content){
        Post updatePost = postRepository.findById(postId);
        updatePost.updatePost(title, boardName, content);
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

    @Transactional
    public Post createPost(Member author, BoardName boardName, String title, String content) {
        Post post = new Post();
        post.createPost(title, author, boardName, content);
        return post;
    }
}
