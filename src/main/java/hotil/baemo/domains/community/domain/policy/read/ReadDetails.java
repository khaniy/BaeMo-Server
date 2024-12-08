package hotil.baemo.domains.community.domain.policy.read;

@FunctionalInterface
public interface ReadDetails<T, U, R, V> {
    V load(T t, U u, R r);
}