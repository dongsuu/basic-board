package donghyun.basicboard;

import donghyun.basicboard.domain.Board;
import donghyun.basicboard.domain.BoardName;
import donghyun.basicboard.domain.Post;
import donghyun.basicboard.service.BoardService;
import donghyun.basicboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitData {

    private final BoardService boardService;
    private final PostService postService;

    @PostConstruct
    public void initBoards(){
        Board board1 = new Board(BoardName.FREE);
        Board board2 = new Board(BoardName.SPORTS);
        Board board3 = new Board(BoardName.STUDY);

        boardService.saveBoards(board1, board2, board3);
        boardService.saveBoards();
    }



}
