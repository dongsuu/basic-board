package donghyun.basicboard.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "UploadFile")
@Getter @Setter
public class UploadFileEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upload_file_id")
    private Long id;

    private UploadFile uploadFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public UploadFileEntity() {
    }

    public static UploadFileEntity createUploadFileEntity(String uploadFileName, String storeFileName){
        UploadFileEntity uploadFileEntity = new UploadFileEntity();
        uploadFileEntity.uploadFile = new UploadFile(uploadFileName, storeFileName);
        return uploadFileEntity;
    }

    public void updatePost(Post post){
        this.post = post;
        post.getUploadFiles().add(this);
    }

    public void createUploadFile(Post post, UploadFileEntity uploadFileEntity) {
        this.uploadFile = uploadFileEntity.uploadFile;
        this.post = post;
    }
}
