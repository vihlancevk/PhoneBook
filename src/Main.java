import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path pathToPhoneBook = Paths.get("data/phoneBook.json");

        try {
            PhoneBookUserInterface.run(pathToPhoneBook);
        } catch (IOException e) {
             e.printStackTrace();
        }
    }
}
