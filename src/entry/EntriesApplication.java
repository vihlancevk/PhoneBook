package entry;

import user.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntriesApplication {
    private static final String url = "jdbc:mysql://localhost:3306/PhoneBook?useSSL=false&useUnicode=true&serverTimezone=UTC";
    private static final String userName = "root";
    private static final String password = "password";

    public static void run(User user) throws IOException {
        try (Connection connection = DriverManager.getConnection(url, userName, password)) {
            System.out.println("Start using the phone book.\n");

            EntryDAO entryDAO = new EntryDAO(connection, user.getUserId());

            handlerUserInput(entryDAO);
        } catch (Exception e) {
            // TODO: logging
        } finally {
            System.out.print("End using the phone book.");
        }
    }

    private static void handlerUserInput(EntryDAO entryDAO) {
        Scanner scanner = new Scanner(System.in);

        String enteredNumber = "";

        while (!enteredNumber.equals("-1")) {
            printInfo();

            enteredNumber = scanner.nextLine();

            switch (enteredNumber) {
                case "0": {
                    System.out.println("  - show");
                    show(entryDAO, scanner);
                    break;
                }
                case "1": {
                    System.out.println("  - add entry");
                    addEntry(entryDAO, scanner);
                    break;
                }
                case "2": {
                    System.out.println("  - delete entry");
                    delEntry(entryDAO, scanner);
                    break;
                }
                case "-1": {
                    System.out.println("   - stop\n");
                    break;
                }
                default: {
                    System.out.println("You entered wrong number of command. Please, try again:");
                    wait(scanner);
                }
            }
        }
    }

    private static void printInfo() {
        System.out.println("Choose a number:\n" +
                " 0 - show entries\n" +
                " 1 - add entry\n" +
                " 2 - delete entry\n" +
                "-1 - stop\n");
    }

    private static void show(EntryDAO entryDAO, Scanner scanner) {
        System.out.println(entryDAO);

        wait(scanner);
    }

    private static void addEntry(EntryDAO entryDAO, Scanner scanner) {
        System.out.print("Enter ");
        String name = enterName(scanner);

        System.out.print("Enter ");
        Optional<String> number = enterNumber(scanner);

        if (number.isPresent())
            if (entryDAO.save(new Entry(entryDAO.getUserId(), name, number.get())).isPresent())
                System.out.println("OK.");
            else
                System.out.println("Error.");

        wait(scanner);
    }

    private static void delEntry(EntryDAO entryDAO, Scanner scanner) {
        System.out.print("Enter ");

        String name = enterName(scanner);

        entryDAO.delete(name);

        wait(scanner);
    }

    private static String enterName(Scanner scanner) {
        System.out.print("name: ");
        return scanner.nextLine();
    }

    private static Optional<String> enterNumber(Scanner scanner) {
        System.out.print("number like this - +7(963)873-13-93: ");

        int counterOfWrongAttempt = 0;

        Pattern pattern = Pattern.compile("[+][0-9][(][0-9]{3}[)][0-9]{3}(-[0-9]{2}){2}");

        String number;
        Matcher matcher;
        while (true) {
            number = scanner.nextLine();
            if (number.isEmpty() && counterOfWrongAttempt > 0) {
                System.out.println("Command execution was interrupted.");
                return Optional.empty();
            }

            matcher = pattern.matcher(number);

            if (matcher.find())
                return Optional.of(number);
            else {
                System.out.println("You entered wrong number. If you want go back press Enter else try again:");
                counterOfWrongAttempt++;
            }
        }
    }

    private static void wait(Scanner scanner) {
        System.out.println("Press Enter to continue.");
        scanner.nextLine();
    }
}
