package donghyun.basicboard.repository;

import donghyun.basicboard.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentRepositoryTest {

    @Autowired CommentRepository commentRepository;
    @Autowired PostRepository postRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired BoardRepository boardRepository;

    @Test
    @Rollback(value = false)
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
//        assertThat(findComment).isEqualTo(comment);

    }

}