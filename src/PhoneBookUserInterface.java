import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;

public class PhoneBookUserInterface {
    public static void run(Path pathToPhoneBook) throws IOException {
        System.out.println("You use phone book.\n");

        PhoneBookJsonHandler phoneBookHandler = new PhoneBookJsonHandler();

        System.out.println("Read phone book...\n");
        PhoneBook phoneBook = phoneBookHandler.read(pathToPhoneBook);

        handlerUserInput(phoneBook);

        System.out.println("Write phone book...\n");
        phoneBookHandler.write(pathToPhoneBook, phoneBook);

        System.out.print("End use of phone book.");
    }

    private static void handlerUserInput(PhoneBook phoneBook) {
        Scanner scanner = new Scanner(System.in);

        String enteredNumber = "";

        while (!enteredNumber.equals("-1")) {
            printInfo();

            enteredNumber = scanner.next();

            switch (enteredNumber) {
                case "0": {
                    System.out.println("  - show");
                    show(phoneBook, scanner);
                    break;
                }
                case "1": {
                    System.out.println("  - add entry");
                    addEntry(phoneBook, scanner);
                    break;
                }
                case "2": {
                    System.out.println("  - delete entry by name");
                    delEntryByName(phoneBook, scanner);
                    break;
                }
                case "3": {
                    System.out.println("  - delete entry by number");
                    delEntryByNumber(phoneBook, scanner);
                    break;
                }
                case "4": {
                    System.out.println("  - get number by name");
                    getNumberByName(phoneBook, scanner);
                    break;
                }
                case "5": {
                    System.out.println("  - get name by number");
                    getNameByNumber(phoneBook, scanner);
                    break;
                }
                case "6": {
                    System.out.println("  - set number by name");
                    setNumberByName(phoneBook, scanner);
                    break;
                }
                case "7": {
                    System.out.println("  - set name by number");
                    setNameByNumber(phoneBook, scanner);
                    break;
                }
                case "8": {
                    System.out.println("  - delete all entries");
                    clear(phoneBook, scanner);
                    break;
                }
                case "9": {
                    System.out.println("  - check that there are entries");
                    isEmpty(phoneBook, scanner);
                    break;
                }
                case "-1": {
                    System.out.println("   - stop\n");
                    break;
                }
                default: {

                }
            }
        }
    }

    private static void printInfo() {
        System.out.println("Enter a number that represents your intent:\n" +
                " 0 - show phone book\n" +
                " 1 - add entry\n" +
                " 2 - delete entry by name\n" +
                " 3 - delete entry by number\n" +
                " 4 - get number by name\n" +
                " 5 - get name by number\n" +
                " 6 - set number by name\n" +
                " 7 - set name by number\n" +
                " 8 - delete all entries\n" +
                " 9 - check that there are entries\n" +
                "-1 - stop\n");
    }

    private static void show(PhoneBook phoneBook, Scanner scanner) {
        System.out.println(phoneBook);

        wait(scanner);
    }

    private static void addEntry(PhoneBook phoneBook, Scanner scanner) {
        System.out.print("Enter ");
        String name = enterName(scanner);

        System.out.print("Enter ");
        String number = enterNumber(scanner);

        System.out.println(phoneBook.addEntry(name, number));

        wait(scanner);
    }

    private static void delEntryByName(PhoneBook phoneBook, Scanner scanner) {
        System.out.print("Enter ");

        String name = enterName(scanner);

        System.out.println(phoneBook.delEntryByName(name));

        wait(scanner);
    }

    private static void delEntryByNumber(PhoneBook phoneBook, Scanner scanner) {
        System.out.print("Enter ");

        String number = enterNumber(scanner);

        System.out.println(phoneBook.delEntryByNumber(number));

        wait(scanner);
    }

    private static void getNumberByName(PhoneBook phoneBook, Scanner scanner) {
        System.out.print("Enter ");

        String name = enterName(scanner);

        Optional<String> number = phoneBook.getNumberByName(name);
        if (number.isEmpty())
            System.out.println(PhoneBook.PhoneBookStatus.ERROR_NAME_NOT_EXIST);
        else
            System.out.println(number.get());

        wait(scanner);
    }

    private static void getNameByNumber(PhoneBook phoneBook, Scanner scanner) {
        System.out.print("Enter ");

        String number = enterNumber(scanner);

        Optional<String> name = phoneBook.getNameByNumber(number);
        if (name.isEmpty())
            System.out.println(PhoneBook.PhoneBookStatus.ERROR_NUMBER_NOT_EXIST);
        else
            System.out.println(name.get());

        wait(scanner);
    }

    private static void setNumberByName(PhoneBook phoneBook, Scanner scanner) {
        System.out.print("Enter ");
        String name = enterName(scanner);

        System.out.print("Enter new ");
        String newNumber = enterNumber(scanner);

        System.out.println(phoneBook.setNumberByName(name, newNumber));

        wait(scanner);
    }

    private static void setNameByNumber(PhoneBook phoneBook, Scanner scanner) {
        System.out.print("Enter new ");
        String newName = enterName(scanner);

        System.out.print("Enter ");
        String number = enterNumber(scanner);

        System.out.println(phoneBook.setNameByNumber(newName, number));

        wait(scanner);
    }

    private static void clear(PhoneBook phoneBook, Scanner scanner) {
        phoneBook.clear();
        System.out.println("Phone book is cleared.");
        wait(scanner);
    }

    private static void isEmpty(PhoneBook phoneBook, Scanner scanner) {
        if (phoneBook.isEmpty())
            System.out.println("Phone book is empty.");
        else
            System.out.println("Phone book isn't empty.");
        wait(scanner);
    }

    private static String enterName(Scanner scanner) {
        System.out.print("name: ");
        return scanner.next();
    }

    private static String enterNumber(Scanner scanner) {
        System.out.print("number like this '+7(963)873-13-93: ");
        return scanner.next();
    }

    private static void wait(Scanner scanner) {
        // Handle new line after enter number
        scanner.nextLine();

        System.out.println("Press Enter to continue.");
        scanner.nextLine();
    }
}