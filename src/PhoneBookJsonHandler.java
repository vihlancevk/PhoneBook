import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class PhoneBookJsonHandler {
    private final ObjectMapper mapper;

    public PhoneBookJsonHandler() {
        mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    public PhoneBook read(Path pathToPhoneBook) throws IOException {
        try (InputStream inputStream = Files.newInputStream(pathToPhoneBook)) {
            return mapper.readerFor(PhoneBook.class).readValue(inputStream);
        } catch (IOException e) {
            throw new IOException("Error during read phone book", e);
        }
    }

    public void write(Path pathToPhoneBook, PhoneBook phoneBook) throws IOException {
        try (OutputStream outputStream = Files.newOutputStream(pathToPhoneBook)) {
            mapper.writeValue(outputStream, phoneBook);
        } catch (IOException e) {
            throw new IOException("Error during write phone book", e);
        }
    }
}
