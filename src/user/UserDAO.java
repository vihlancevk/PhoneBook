package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import interfaces.DAO;

public class UserDAO implements DAO<User> {
    private final Connection connection;
    private final String tableName = "users";

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<User> get(String key) {
        String query = "SELECT * FROM " + tableName + " WHERE login = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return Optional.of(
                    new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3))
                );
        } catch (SQLException e) {
            // TODO: logging
        }

        return Optional.empty();
    }

    /*
    This method don't realized.
     */
    @Override
    public List<User> getAll() {
        throw new IllegalStateException();
    }

    @Override
    public Optional<User> save(User user) {
        // Check, that there is not user with same login
        if (get(user.getLogin()).isPresent())
            return Optional.empty();

        String query = "INSERT INTO " + tableName + " (login, password) Values (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());

            int rows = preparedStatement.executeUpdate();

            if (rows == 1)
                return get(user.getLogin());
        } catch (SQLException e) {
            // TODO: logging
        }

        return Optional.empty();
    }

    /*
    This method don't realized.
     */
    @Override
    public void update(User user, String[] params) {
        throw new IllegalStateException();
    }

    /*
    This method don't realized.
     */
    @Override
    public void delete(String key) {
        throw new IllegalStateException();
    }
}
