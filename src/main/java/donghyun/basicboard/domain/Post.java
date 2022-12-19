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

    @Enumerated
    private BoardName boardName;

    @NotEmpty
    private String content;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "upload_file_id")
    private List<UploadFileEntity> uploadFiles = new ArrayList<>();

    public Post() {
    }


    public void createPost(String title, Member author, BoardName boardName, String content){
        this.title = title;
        this.author = author;
        this.boardName = boardName;
        this.content = content;
    }

    public void updatePost(String title, BoardName boardName, String content){
        this.title = title;
        this.boardName = boardName;
        this.content = content;
    }

    public void addUploadFile(UploadFileEntity... uploadFiles){
        this.uploadFiles.addAll(Arrays.asList(uploadFiles));
    }
}
