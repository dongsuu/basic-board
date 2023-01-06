package donghyun.basicboard.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
public class Post extends DateEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @NotEmpty
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    @Enumerated(value = EnumType.STRING)
    private BoardName boardName;

    @NotEmpty
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UploadFileEntity> uploadFiles = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    public Post() {
    }


    public void createPost(String title, Member author, BoardName boardName, String content, List<UploadFileEntity> uploadFiles){
        this.title = title;
        this.author = author;
        this.boardName = boardName;
        this.content = content;
        for (UploadFileEntity uploadFile : uploadFiles) {
            this.uploadFiles.add(uploadFile);
            uploadFile.setPost(this);
        }

        author.getPosts().add(this); // 연관관계 편의 메서드
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
