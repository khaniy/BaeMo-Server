package hotil.baemo.domains.users.adapter.output.persistence.repository.base;

public interface BaseAbstractBaeMoUsersEntityJpaRepository {
    boolean existsByPhone(String phone);
}
