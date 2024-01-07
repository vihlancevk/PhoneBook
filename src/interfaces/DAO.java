package interfaces;

import user.User;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    Optional<T> get(String key);

    List<T> getAll();

    Optional<T> save(T t);

    void update(T t, String[] params);

    void delete(String key);
}
