package donghyun.basicboard.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Comment extends DateEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> replies = new ArrayList<>();

    public Comment() {
    }

    public static Comment createComment(Member author, String content, Post post){
        Comment comment = new Comment();
        comment.author = author;
        comment.content = content;
        comment.post = post;
        author.getComments().add(comment); // 연관관계 편의 메서드
        return comment;
    }

    public void addReply(Comment child) {
        this.replies.add(child);
        child.parentComment = this;
    }
}
