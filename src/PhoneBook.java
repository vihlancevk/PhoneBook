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

    public void clear() {
        name2number.clear();
    }

    public boolean isEmpty() {
        return name2number.isEmpty();
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

    public PhoneBookStatus setNumberByName(String name, String newNumber) {
        if (!name2number.containsKey(name))
            return PhoneBookStatus.ERROR_NAME_NOT_EXIST;

        name2number.put(name, newNumber);

        return PhoneBookStatus.OK;
    }

    public PhoneBookStatus setNameByNumber(String newName, String number) {
        PhoneBookStatus status = delEntryByNumber(number);

        if (status.equals(PhoneBookStatus.OK))
            status = addEntry(newName, number);

        return status;
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
}
