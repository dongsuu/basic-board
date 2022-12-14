package donghyun.basicboard.repository;

import donghyun.basicboard.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public Long save(Board board){
        em.persist(board);
        return board.getId();
    }
}
