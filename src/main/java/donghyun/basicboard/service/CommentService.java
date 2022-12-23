package donghyun.basicboard.service;

import donghyun.basicboard.domain.BoardName;
import donghyun.basicboard.domain.Comment;
import donghyun.basicboard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Comment는 수정 불가능
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Long addComment(Comment comment){
        commentRepository.save(comment);
        return comment.getId();
    }

    public List<Comment> findAllByBoardName(BoardName boardName){
        List<Comment> allByBoardName = commentRepository.findAllByBoardName(boardName);
        return allByBoardName;
    }

    public List<Comment> findAllByPostId(Long postId){
        List<Comment> result = commentRepository.findAllByPostId(postId);
        return result;
    }

    public void removeComment(Long commentId){
        Comment removeComment = commentRepository.findById(commentId);
        commentRepository.remove(removeComment);
    }
}
