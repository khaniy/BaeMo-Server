package hotil.baemo.domains.users.adapter.output.persistence.repository;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.users.adapter.output.persistence.entity.SocialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SocialJpaRepository extends JpaRepository<SocialEntity, Long> {

    @Override
    @Query("SELECT u FROM SocialEntity u WHERE u.id = :id AND u.isDel = false")
    Optional<SocialEntity> findById(@Param("id") Long id);

    @Query("SELECT u FROM SocialEntity u WHERE u.phone = :phone AND u.isDel = false")
    Optional<SocialEntity> findByPhone(@Param("phone") String phone);

    @Query("SELECT u FROM SocialEntity u WHERE u.oauthId = :oauthId AND u.isDel = false")
    Optional<SocialEntity> findByOauthId(@Param("oauthId") String oauthId);

    default SocialEntity loadById(Long id) {
        return findById(id)
            .orElseThrow(() -> new CustomException(ResponseCode.USERS_NOT_FOUND));
    }

    default SocialEntity loadByPhone(String phone) {
        return findByPhone(phone)
            .orElseThrow(() -> new CustomException(ResponseCode.USERS_NOT_FOUND));
    }

    default SocialEntity loadByOauthId(String oauth2Id) {
        return findByOauthId(oauth2Id)
            .orElseThrow(() -> new CustomException(ResponseCode.USERS_NOT_FOUND));
    }
}