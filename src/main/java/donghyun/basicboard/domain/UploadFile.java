package donghyun.basicboard.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class UploadFile {
    private String uploadFileName;
    private String storeFileName;

    public UploadFile() {
    }

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UploadFile that = (UploadFile) o;
        return Objects.equals(uploadFileName, that.uploadFileName) && Objects.equals(storeFileName, that.storeFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uploadFileName, storeFileName);
    }
}
