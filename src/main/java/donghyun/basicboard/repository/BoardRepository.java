package donghyun.basicboard.repository;

import donghyun.basicboard.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public Long save(Board board){
        em.persist(board);
        return board.getId();
    }

    public List<Board> findAll(){
        String jpql = "select b from Board b";
        List<Board> result = em.createQuery(jpql, Board.class)
                .getResultList();

        return result;

    }
}
