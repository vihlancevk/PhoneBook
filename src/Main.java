import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path pathToPhoneBook = Paths.get("phoneBook.json");
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        try {
            PhoneBook phoneBook = readPhoneBook(pathToPhoneBook, mapper);

            System.out.print(phoneBook);

            savePhoneBook(pathToPhoneBook, mapper, phoneBook);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static PhoneBook readPhoneBook(Path pathToPhoneBook, ObjectMapper mapper) throws IOException {
        try (InputStream inputStream = Files.newInputStream(pathToPhoneBook)) {
            return mapper.readerFor(PhoneBook.class).readValue(inputStream);
        } catch (IOException e) {
            throw new IOException("Error during read phone book", e);
        }
    }

    private static void savePhoneBook(Path pathToPhoneBook, ObjectMapper mapper, PhoneBook phoneBook) throws IOException {
        try (OutputStream outputStream = Files.newOutputStream(pathToPhoneBook)) {
            mapper.writeValue(outputStream, phoneBook);
        } catch (IOException e) {
            throw new IOException("Error during save phone book", e);
        }
    }
}
