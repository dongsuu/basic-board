package donghyun.basicboard.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "UploadFile")
@Getter
public class UploadFileEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upload_file_id")
    private Long id;

    private UploadFile uploadFile;

    public UploadFileEntity() {
    }

    public UploadFileEntity(String uploadFileName, String storeFileName){
        this.uploadFile = new UploadFile(uploadFileName, storeFileName);
    }

}
