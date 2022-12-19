package donghyun.basicboard.service;

import donghyun.basicboard.domain.Board;
import donghyun.basicboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void saveBoards(Board... board){
        for (Board b : board) {
            boardRepository.save(b);
        }
    }
    public List<Board> findAll(){
        List<Board> result = boardRepository.findAll();
        return result;
    }
}
