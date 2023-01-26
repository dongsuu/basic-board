package donghyun.basicboard.repository;

import donghyun.basicboard.domain.UploadFileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UploadFileRepository {
    private final EntityManager em;

    public UploadFileEntity findById(Long uploadFileId){
        return em.createQuery("select u from UploadFileEntity u where u.id=:uploadFileId", UploadFileEntity.class)
                .setParameter("uploadFileId", uploadFileId)
                .getSingleResult();
    }

    @Transactional
    public void remove(UploadFileEntity uploadFileEntity){
        em.remove(uploadFileEntity);
    }
}
