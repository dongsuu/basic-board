package donghyun.basicboard.repository;

import donghyun.basicboard.domain.Board;
import donghyun.basicboard.domain.BoardName;
import donghyun.basicboard.domain.Member;
import donghyun.basicboard.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired private PostRepository postRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private BoardRepository boardRepository;

    @Test
    public void post_save(){
        //given
        Member member = createMember();
        memberRepository.save(member);

        Board board = new Board(BoardName.FREE);
        boardRepository.save(board);

        Post post = new Post();
        post.createPost("title1", member, board, "hello~~~");

        //when
        Long savedId = postRepository.save(post);

        //then
        Post findPost = postRepository.findById(savedId);
        assertThat(savedId).isEqualTo(post.getId());
        assertThat(findPost).isEqualTo(post);

    }

    @Test
    public void post_findAll(){
        //given
        Member member = createMember();
        memberRepository.save(member);

        Board board1 = new Board(BoardName.FREE);
        boardRepository.save(board1);
        Board board2 = new Board(BoardName.SPORTS);
        boardRepository.save(board2);

        Post post1 = new Post();
        post1.createPost("title1", member, board1, "hello~~~");
        postRepository.save(post1);

        Post post2 = new Post();
        post2.createPost("title2", member, board2, "hihi");
        postRepository.save(post2);

        //when
        List<Post> findPosts = postRepository.findAll();


        //then
        assertThat(findPosts.size()).isEqualTo(2);
        assertThat(findPosts.get(0).getAuthor()).isEqualTo(member);
    }

    private Member createMember() {
        Member member = new Member();
        member.createMember("memberA", "memA", 30, "id", "pw");
        return member;
    }

}