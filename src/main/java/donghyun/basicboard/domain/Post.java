package donghyun.basicboard.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
public class Post extends DateEntity{

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @NotEmpty
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @NotEmpty
    private String content;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "upload_file_id")
    private List<UploadFileEntity> uploadFiles = new ArrayList<>();

    public Post() {
    }


    public void createPost(String title, Member author, Board board, String content){
        this.title = title;
        this.author = author;
        this.board = board;
        this.content = content;
    }

    public void updatePost(String title, Board board, String content){
        this.title = title;
        this.board = board;
        this.content = content;
    }

    public void addUploadFile(UploadFileEntity... uploadFiles){
        this.uploadFiles.addAll(Arrays.asList(uploadFiles));
    }
}
