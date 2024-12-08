package hotil.baemo.domains.users.adapter.output.persistence.repository;

import hotil.baemo.domains.users.adapter.output.persistence.entity.UserLocationEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserLocationJpaRepository extends JpaRepository<UserLocationEntity, Long> {


    @Modifying
    @Query("DELETE FROM UserLocationEntity e WHERE e.userId=:userId")
    void deleteByUserId(@Param("userId") Long userId);
}
