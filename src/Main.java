import entry.EntriesApplication;
import user.User;
import user.UsersApplication;

import java.io.IOException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        try {
            Optional<User> user = UsersApplication.run();
            if (user.isEmpty())
                System.out.print("You don't log in or sign in.");
            else
                EntriesApplication.run(user.get());
        } catch (IOException e) {
             // TODO: logging
        }
    }
}
