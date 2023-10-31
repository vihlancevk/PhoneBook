package interfaces;

import user.User;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(String name);

    List<T> getAll();

    Optional<User> save(T t);

    void update(T t, String[] params);

    void delete(T t);
}
