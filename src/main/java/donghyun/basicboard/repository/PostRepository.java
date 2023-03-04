package donghyun.basicboard.repository;

import donghyun.basicboard.domain.BoardName;
import donghyun.basicboard.domain.Post;
import donghyun.basicboard.domain.UploadFileEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostRepository {

    private final EntityManager em;
    private final UploadFileRepository uploadFileRepository;

    public Long save(Post post){
        em.persist(post);
        return post.getId();
    }

    public Post findById(Long postId){
        Post findPost = em.find(Post.class, postId);
        return findPost;
    }

    public List<Post> findByBoardName(BoardName boardName){
        log.info("boardName={}", boardName);
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

    public Post findByUploadFileId(Long uploadFileId){
        UploadFileEntity findUploadFile = uploadFileRepository.findById(uploadFileId);
        Long postId = findUploadFile.getPost().getId();
        String jpql = "select p from Post p where p.id=:postId";
        return em.createQuery(jpql, Post.class)
                .setParameter("postId", postId)
                .getSingleResult();
    }

    public void remove(Post post){
        em.remove(post);
    }

}
