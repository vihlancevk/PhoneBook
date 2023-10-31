package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import interfaces.Dao;

public class UserDao implements Dao<User> {
    private final Connection connection;
    private final String tableName = "users";

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<User> get(String name) {
        String query = "SELECT * FROM " + tableName + " WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return Optional.of(new User(resultSet.getString(1), resultSet.getString(2)));
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
        String query = "SELECT COUNT(*) FROM ? WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, tableName);
            preparedStatement.setString(2, user.getUsername());

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
                if (resultSet.getInt(1) != 0)
                    return Optional.empty();
        } catch (SQLException e) {
            // TODO: logging
        }

        query = "INSERT INTO " + tableName + " (username, password) Values (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            int rows = preparedStatement.executeUpdate();

            if (rows == 1)
                return Optional.of(user);
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
    public void delete(User user) {
        throw new IllegalStateException();
    }
}
