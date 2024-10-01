package hotil.baemo.domains.users.adapter.output.persistence.repository;

import hotil.baemo.domains.users.adapter.output.persistence.entity.DeviceEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DeviceJpaRepository extends JpaRepository<DeviceEntity, String> {

    @Override
    @Query("SELECT e FROM DeviceEntity e WHERE e.uniqueId=:id AND e.isDel=false")
    Optional<DeviceEntity> findById(@Param("id") String id);

    @Modifying
    @Query("DELETE FROM DeviceEntity e WHERE e.uniqueId=:id")
    void deleteByUniqueId(@NotBlank String id);
}
