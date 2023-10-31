package user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import interfaces.JsonHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class UsersJsonHandler implements JsonHandler<UserDao> {
    private final ObjectMapper mapper;

    public UsersJsonHandler() {
        mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    public UserDao read(Path pathToUsers) throws IOException {
        try (InputStream inputStream = Files.newInputStream(pathToUsers)) {
            return mapper.readerFor(UserDao.class).readValue(inputStream);
        } catch (IOException e) {
            throw new IOException("Error during read users", e);
        }
    }

    public void write(Path pathToUsers, UserDao userDao) throws IOException {
        try (OutputStream outputStream = Files.newOutputStream(pathToUsers)) {
            mapper.writeValue(outputStream, userDao);
        } catch (IOException e) {
            throw new IOException("Error during write users", e);
        }
    }
}
