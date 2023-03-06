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

//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<UploadFileEntity> uploadFiles = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    public Post() {
    }


    public static Post createPost(String title, Member author, BoardName boardName, String content){
        Post post = new Post();
        post.title = title;
        post.author = author;
        post.boardName = boardName;
        post.content = content;
//        post.uploadFiles = uploadFiles;
//
//        for (UploadFileEntity uploadFile : uploadFiles) {
//            uploadFile.setPost(post); // 연관관계 편의 메서드
//        }
//        author.getPosts().add(post); // 연관관계 편의 메서드
        return post;
    }

    public void updatePost(String title, BoardName boardName, String content, List<UploadFileEntity> uploadFileEntities){
        this.title = title;
        this.boardName = boardName;
        this.content = content;
        this.uploadFiles = uploadFileEntities;
    }


    public void addUploadFile(UploadFileEntity... uploadFiles){
        this.uploadFiles.addAll(Arrays.asList(uploadFiles));
    }
    public void removeUploadFile(UploadFileEntity uploadFileEntity){
        this.uploadFiles.remove(uploadFileEntity);
        uploadFileEntity.setPost(null);
    }
}
