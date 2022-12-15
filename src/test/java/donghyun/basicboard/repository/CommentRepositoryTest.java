package donghyun.basicboard.repository;

import donghyun.basicboard.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentRepositoryTest {

    @Autowired CommentRepository commentRepository;
    @Autowired PostRepository postRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired BoardRepository boardRepository;

    @Test
    public void save_comment(){
        //given
        Member member = new Member();
        member.createMember("mA", "mm", 30, "id", "pw");
        memberRepository.save(member);

        Board board = new Board(BoardName.STUDY);
        boardRepository.save(board);

        Post post = new Post();
        post.createPost("title", member, board, "content!!");
        postRepository.save(post);

        Comment comment = new Comment();
        comment.createComment(member, "comment-content", post);


        //when
        Long savedId = commentRepository.save(comment);
        Comment findComment = commentRepository.findById(savedId);

        //then
        assertThat(savedId).isEqualTo(comment.getId());
        assertThat(findComment).isEqualTo(comment);

    }

    @Test
    public void findByBoardName(){
        //given
        Member member1 = new Member();
        member1.createMember("mA", "mm", 30, "id", "pw");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.createMember("test", "tttt", 20, "id12", "pw12");
        memberRepository.save(member2);

        Board board1 = new Board(BoardName.STUDY);
        Board board2 = new Board((BoardName.SPORTS));
        boardRepository.save(board1);
        boardRepository.save(board2);

        Post post1 = new Post();
        Post post2 = new Post();
        post1.createPost("title", member1, board1, "content!!");
        post2.createPost("title", member2, board2, "board2 content!!");
        postRepository.save(post1);
        postRepository.save(post2);

        Comment comment1 = new Comment();
        comment1.createComment(member1, "comment-content", post1);
        Comment comment2 = new Comment();
        comment2.createComment(member2, "co2", post1);
        Comment comment3 = new Comment();
        comment3.createComment(member2, "memb2comment", post2);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        //when
        List<Comment> allByBoardName = commentRepository.findAllByBoardName(BoardName.STUDY);
        List<Comment> allByBoardName1 = commentRepository.findAllByBoardName(BoardName.SPORTS);

        //then
        assertThat(allByBoardName.size()).isEqualTo(2);
        assertThat(allByBoardName1.size()).isEqualTo(1);
    }

}