package user;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;
import java.util.Scanner;

public class UsersApplication {
    private static final String url = "jdbc:mysql://localhost:3306/PhoneBook?useSSL=false&useUnicode=true&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "password";

    public static Optional<User> run() throws IOException {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Start using phone book.\n");

            UserDAO userDAO = new UserDAO(connection);

            return handlerUserInput(userDAO);
        } catch (Exception e) {
            // TODO: logging
        }

        return Optional.empty();
    }

    private static Optional<User> handlerUserInput(UserDAO userDAO) {
        Scanner scanner = new Scanner(System.in);

        String enteredNumber;

        while (true) {
            printInfo();

            enteredNumber = scanner.nextLine();

            switch (enteredNumber) {
                case "1": {
                    Optional<User> user = signUp(userDAO, scanner);
                    if (user.isPresent()) {
                        System.out.println("You successfully sign up.");
                        wait(scanner);
                        return user;
                    } else {
                        System.out.println("This login already exist. Please, try again:");
                        wait(scanner);
                    }
                    break;
                }
                case "2": {
                    Optional<User> user = logIn(userDAO, scanner);
                    if (user.isPresent()) {
                        System.out.println("You successfully log in.");
                        wait(scanner);
                        return user;
                    } else {
                        System.out.println("This login or password incorrect. Please, try again:");
                        wait(scanner);
                    }
                    break;
                }
                case "-1": {
                    System.out.println("   - stop\n");
                    return Optional.empty();
                }
                default: {
                    System.out.println("You entered wrong number of command. Please, try again:");
                    wait(scanner);
                }
            }
        }
    }

    private static void printInfo() {
        System.out.println("Enter a number that represents your intent:\n" +
                " 1 - sign up\n" +
                " 2 - log in\n" +
                "-1 - stop\n");
    }

    private static Optional<User> signUp(UserDAO userDAO, Scanner scanner) {
        System.out.print("Enter ");
        String login = enterLogin(scanner);

        System.out.print("Enter ");
        String password = enterPassword(scanner);

        User user = new User(login, password);

        return userDAO.save(user);
    }

    private static Optional<User> logIn(UserDAO userDAO, Scanner scanner) {
        System.out.print("Enter ");
        String login = enterLogin(scanner);

        System.out.print("Enter ");
        String password = enterPassword(scanner);

        Optional<User> user = userDAO.get(login);
        if (user.isEmpty())
            return user;

        if (user.get().getPassword().equals(password))
            return user;

        return Optional.empty();
    }

    private static void wait(Scanner scanner) {
        System.out.println("Press Enter to continue.");
        scanner.nextLine();
    }

    private static String enterLogin(Scanner scanner) {
        System.out.print("login: ");
        return scanner.nextLine();
    }

    private static String enterPassword(Scanner scanner) {
        System.out.print("password: ");
        return scanner.nextLine();
    }
}
