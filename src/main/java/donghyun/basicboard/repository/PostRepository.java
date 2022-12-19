package donghyun.basicboard.repository;

import donghyun.basicboard.domain.BoardName;
import donghyun.basicboard.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public Long save(Post post){
        em.persist(post);
        return post.getId();
    }

    public Post findById(Long postId){
        Post findPost = em.find(Post.class, postId);
        return findPost;
    }

    public List<Post> findByBoardName(BoardName boardName){
        String jpql = "select p from Post p where p.boardName = :boardName";
        List<Post> posts = em.createQuery(jpql, Post.class)
                .setParameter("boardName", boardName)
                .getResultList();
        return posts;
    }

    public List<Post> findAll(){
        String jpql = "select p from Post p";
        List<Post> posts = em.createQuery(jpql, Post.class)
                .getResultList();
        return posts;
    }

    public void remove(Post post){
        em.remove(post);
    }

}
