import phone.book.PhoneBookApplication;
import user.User;
import user.UserApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        try {
            Optional<User> user = UserApplication.run();
            if (user.isEmpty()) {
                System.out.print("You don't log in or sign in.");
            } else {
                Path pathToPhoneBook = Paths.get("data/phoneBooks/" + user.get().getUsername() + "PhoneBook.json");

                handlePath(pathToPhoneBook);

                PhoneBookApplication.run(pathToPhoneBook);
            }
        } catch (IOException e) {
             // TODO: logging
        }
    }

    private static void handlePath(Path path) throws IOException {
        if (!Files.exists(path))
            Files.createFile(path);
    }
}
