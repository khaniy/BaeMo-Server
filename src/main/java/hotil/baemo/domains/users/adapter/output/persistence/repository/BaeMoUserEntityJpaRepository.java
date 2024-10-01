package hotil.baemo.domains.users.adapter.output.persistence.repository;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.users.adapter.output.persistence.entity.BaeMoUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BaeMoUserEntityJpaRepository extends JpaRepository<BaeMoUserEntity, Long> {

    @Override
    @Query("SELECT u FROM BaeMoUserEntity u WHERE u.id = :id AND u.isDel = false")
    Optional<BaeMoUserEntity> findById(@Param("id") Long id);


    @Query("SELECT u FROM BaeMoUserEntity u WHERE u.phone = :phone AND u.isDel = false")
    Optional<BaeMoUserEntity> findByPhone(@Param("phone") String phone);

    default BaeMoUserEntity loadById(Long id) {
        return this.findById(id)
            .orElseThrow(() -> new CustomException(ResponseCode.USERS_NOT_FOUND));
    }

    default BaeMoUserEntity loadByPhone(String phone) {
        return this.findByPhone(phone)
            .orElseThrow(() -> new CustomException(ResponseCode.USERS_NOT_FOUND));
    }
}