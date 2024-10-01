package hotil.baemo.domains.users.adapter.output.persistence.dsl;

public interface BaseAbstractBaeMoUsersJpaRepository {
    boolean existsByPhoneNotDel(String phone);

    boolean existsByIdNotDel(Long id);
}
