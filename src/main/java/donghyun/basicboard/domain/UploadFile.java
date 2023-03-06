package donghyun.basicboard.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class UploadFile {
    private String s3FilePath; // s3 file path
    private String originalFileName; // 원본 파일 이름

    public UploadFile() {
    }

    public UploadFile(String uploadFileName, String storeFileName) {
        this.s3FilePath = uploadFileName;
        this.originalFileName = storeFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UploadFile that = (UploadFile) o;
        return Objects.equals(s3FilePath, that.s3FilePath) && Objects.equals(originalFileName, that.originalFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(s3FilePath, originalFileName);
    }
}
