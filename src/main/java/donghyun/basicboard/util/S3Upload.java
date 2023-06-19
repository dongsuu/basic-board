package donghyun.basicboard.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import donghyun.basicboard.domain.Member;
import donghyun.basicboard.domain.Post;
import donghyun.basicboard.domain.UploadFileEntity;
import donghyun.basicboard.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Upload {
    private final AmazonS3 amazonS3;
    private final UploadFileRepository uploadFileRepository;

    private String bucket = "basic-board";

    public String upload(MultipartFile multipartFile, Long postId) throws IOException {

        String s3FileName = multipartFile.getOriginalFilename();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getInputStream().available());

        amazonS3.putObject(bucket,  postId.toString() + "/" + s3FileName, multipartFile.getInputStream(), objectMetadata);

        return amazonS3.getUrl(bucket, postId + "/" + s3FileName).toString();
    }

    public List<String> getUploadFiles(Post post) {
        List<String> uploadFileS3Paths = new ArrayList<>();
        List<UploadFileEntity> uploadFileEntities = uploadFileRepository.findByPost(post);
        for (UploadFileEntity uploadFileEntity : uploadFileEntities) {
            String s3Path = amazonS3.getUrl(bucket, post.getId().toString() + uploadFileEntity.getUploadFile().getS3FilePath()).toString();
            uploadFileS3Paths.add(s3Path);
        }
        return uploadFileS3Paths;
    }
}
