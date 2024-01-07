package entry;

import interfaces.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EntryDAO implements DAO<Entry> {
    private final Connection connection;
    private final int userId;
    private final String tableName = "entries";

    public EntryDAO(Connection connection, int userId) {
        this.connection = connection;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public Optional<Entry> get(String key) {
        String query = "SELECT * FROM " + tableName + " WHERE user_id = ? AND name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return Optional.of(
                    new Entry(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                    )
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
    public List<Entry> getAll() {
        throw new IllegalStateException();
    }

    @Override
    public Optional<Entry> save(Entry entry) {
        // Check, that there is not entry with same name
        if (get(entry.getName()).isPresent())
            return Optional.empty();

        String query = "INSERT INTO " + tableName + " (user_id, name, number) Values (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, entry.getName());
            preparedStatement.setString(3, entry.getNumber());

            int rows = preparedStatement.executeUpdate();

            if (rows == 1)
                return get(entry.getName());
        } catch (SQLException e) {
            // TODO: logging
        }

        return Optional.empty();
    }

    /*
    This method don't realized.
     */
    @Override
    public void update(Entry entry, String[] params) {
        throw new IllegalStateException();
    }

    @Override
    public void delete(String key) {
        // Check, that there is entry with same name
        if (get(key).isEmpty())
            return;

        String query = "DELETE FROM " + tableName + " WHERE user_id = ? AND name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, key);

            int rows = preparedStatement.executeUpdate();

            if (rows == 1)
                return;
        } catch (SQLException e) {
            // TODO: logging
        }
    }
}
