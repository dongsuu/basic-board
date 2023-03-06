package donghyun.basicboard.repository;

import donghyun.basicboard.domain.Post;
import donghyun.basicboard.domain.UploadFileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UploadFileRepository {
    private final EntityManager em;

    public UploadFileEntity findById(Long uploadFileId){
        return em.createQuery("select u from UploadFileEntity u where u.id=:uploadFileId", UploadFileEntity.class)
                .setParameter("uploadFileId", uploadFileId)
                .getSingleResult();
    }

    public void save(UploadFileEntity uploadFileEntity){
        em.persist(uploadFileEntity);
    }

    public List<UploadFileEntity> findByPost(Post post){
        return em.createQuery("select u from UploadFileEntity u where u.post = :post", UploadFileEntity.class)
                .setParameter("post", post)
                .getResultList();
    }

    @Transactional
    public void remove(UploadFileEntity uploadFileEntity){
        em.remove(uploadFileEntity);
    }

    @Transactional
    public void removeAll(List<UploadFileEntity> uploadFiles) {
        for (UploadFileEntity uploadFile : uploadFiles) {
            em.remove(uploadFile);
        }
    }
}
