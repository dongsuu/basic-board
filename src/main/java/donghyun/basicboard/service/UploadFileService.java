package donghyun.basicboard.service;

import donghyun.basicboard.domain.UploadFileEntity;
import donghyun.basicboard.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UploadFileService {
    private final UploadFileRepository uploadFileRepository;

    @Transactional
    public void save(UploadFileEntity uploadFileEntity){
        uploadFileRepository.save(uploadFileEntity);
    }
}
