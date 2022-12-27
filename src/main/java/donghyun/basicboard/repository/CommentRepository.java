package donghyun.basicboard.repository;

import donghyun.basicboard.domain.BoardName;
import donghyun.basicboard.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public Long save(Comment comment){
        em.persist(comment);
        return comment.getId();
    }

    public Comment findById(Long commentId){
        Comment findComment = em.find(Comment.class, commentId);
        return findComment;
    }

    public List<Comment> findAllByBoardName(BoardName boardName){
        String jpql = "select c from Comment c where c.post.board.name = :boardName";
        List<Comment> result = em.createQuery(jpql, Comment.class)
                .setParameter("boardName", boardName)
                .getResultList();
        return result;
    }

    public List<Comment> findAllByPostId(Long postId){
        String jpql = "select c from Comment c where c.post.id = :postId";
        List<Comment> result = em.createQuery(jpql, Comment.class)
                .setParameter("postId", postId)
                .getResultList();
        return result;
    }

    public List<Comment> findAll(){
        String jpql = "select c from Comment c";
        List<Comment> findComments = em.createQuery(jpql, Comment.class)
                .getResultList();
        return findComments;
    }

    public void remove(Comment comment){
        em.remove(comment);
    }

//    public List<Comment> findAllReplies(Long commentId) {
//
//        return replies;
//    }
}
