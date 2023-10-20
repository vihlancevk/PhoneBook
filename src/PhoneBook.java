import java.util.*;
import java.util.Map.Entry;

public class PhoneBook {
    private final Map<String, String> name2number;

    public PhoneBook() {
        this.name2number = new HashMap<>();
    }

    public PhoneBookStatus addEntry(String name, String number) {
        if (name2number.containsKey(name))
            return PhoneBookStatus.ERROR_NAME_EXIST;

        if (name2number.containsValue(number))
            return PhoneBookStatus.ERROR_NUMBER_EXIST;

        name2number.put(name, number);

        return PhoneBookStatus.OK;
    }

    public PhoneBookStatus delEntryByName(String name) {
        if (!name2number.containsKey(name))
            return PhoneBookStatus.ERROR_NAME_NOT_EXIST;

        name2number.remove(name);

        return PhoneBookStatus.OK;
    }

    public PhoneBookStatus delEntryByNumber(String number) {
        Optional<String> name = getNameByNumber(number);

        if (name.isEmpty())
            return PhoneBookStatus.ERROR_NUMBER_NOT_EXIST;

        name2number.remove(name.get(), number);

        return PhoneBookStatus.OK;
    }

    public Optional<String> getNumberByName(String name) {
        if (name2number.containsKey(name))
            return Optional.of(name2number.get(name));

        return Optional.empty();
    }

    public Optional<String> getNameByNumber(String number) {
        for (Entry<String, String> entry : name2number.entrySet()) {
            if (entry.getValue().equals(number))
                return Optional.of(entry.getKey());
        }

        return Optional.empty();
    }

    @Override
    public String toString() {
        StringBuilder resultedString = new StringBuilder();

        for (Entry<String, String> entry : name2number.entrySet())
            resultedString.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");


        return resultedString.toString();
    }

    public enum PhoneBookStatus {
        OK,
        ERROR_NAME_EXIST,
        ERROR_NUMBER_EXIST,
        ERROR_NAME_NOT_EXIST,
        ERROR_NUMBER_NOT_EXIST,
    }

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();

        System.out.println(phoneBook.addEntry("Костя", "+7(9**)***-18-**"));
        System.out.println(phoneBook.addEntry("Андрей", "+7(9**)***-68-**"));
        System.out.println(phoneBook.addEntry("Алечка", "+7(9**)***-18-**"));
        System.out.println(phoneBook.addEntry("Андрей", "+7(9**)***-11-**"));

        System.out.println(phoneBook);

        System.out.println(phoneBook.getNumberByName("Костя"));
        System.out.println(phoneBook.getNumberByName("Алечка"));
        System.out.println(phoneBook.getNameByNumber("+7(9**)***-18-**"));
        System.out.println(phoneBook.getNameByNumber("+7(9**)***-11-**"));

        System.out.println(phoneBook.delEntryByName("Костя"));
        System.out.println(phoneBook);
        System.out.println(phoneBook.delEntryByNumber("+7(9**)***-68-**"));
        System.out.println(phoneBook);
    }
}
