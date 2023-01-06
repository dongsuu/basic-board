package donghyun.basicboard.controller.file;

import donghyun.basicboard.domain.UploadFile;
import donghyun.basicboard.domain.UploadFileEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullFilePath(String fileName){
        return fileDir + fileName;
    }

    public List<UploadFileEntity> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFileEntity> storeResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){
                UploadFileEntity uploadFileEntity = storeFile(multipartFile);
                storeResult.add(uploadFileEntity);
            }
        }
        return storeResult;
    }

    public UploadFileEntity storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFileName);

        multipartFile.transferTo(new File(getFullFilePath(storeFileName)));

        return new UploadFileEntity(originalFileName, storeFileName);

    }

    private String createStoreFileName(String originalFileName){
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();
        String storeFileName = uuid + "." + ext;
        return storeFileName;
    }

    private String extractExt(String originalFileName){
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos);
    }
}
