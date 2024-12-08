package hotil.baemo.domains.users.adapter.output.persistence.repository;

import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.users.adapter.output.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;

import static hotil.baemo.core.common.response.ResponseCode.USERS_NOT_FOUND;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    @Override
    @NonNull
    @Query(
        "SELECT u " +
            "FROM UserEntity u " +
            "WHERE u.id = :id " +
            "AND u.isDel = false"
    )
    Optional<UserEntity> findById(@NonNull @Param("id") Long id);

    @Query(
        "SELECT u " +
            "FROM UserEntity u " +
            "WHERE u.phone = :phone " +
            "AND u.isDel = false"
    )
    Optional<UserEntity> findByPhone(@Param("phone") String phone);

    @Query(
        "SELECT CASE WHEN COUNT(u) > 0 THEN true " +
            "ELSE false END " +
            "FROM UserEntity u " +
            "WHERE u.phone = :phone " +
            "AND u.isDel = false"
    )
    boolean existsByPhone(@Param("phone") String phone);

    @Override
    @Query(
        "SELECT CASE WHEN COUNT(u) > 0 THEN true " +
            "ELSE false END " +
            "FROM UserEntity u " +
            "WHERE u.id = :id " +
            "AND u.isDel = false"
    )
    boolean existsById(@Param("id") @NonNull Long id);

    default UserEntity loadByPhone(String phone) {
        return this.findByPhone(phone)
            .orElseThrow(() -> new CustomException(USERS_NOT_FOUND));
    }

    @Query(
        "SELECT u " +
            "FROM UserEntity u " +
            "WHERE u.id = :userId " +
            "AND u.isDel = false"
    )
    default UserEntity loadById(Long userId) {
        return this.findById(userId)
            .orElseThrow(() -> new CustomException(USERS_NOT_FOUND));
    }
}