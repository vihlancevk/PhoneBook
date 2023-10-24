import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@JsonIncludeProperties("phoneBook")
public class PhoneBook {
    private final Map<String, String> phoneBook;

    // name to number
    public PhoneBook() {
        this.phoneBook = new HashMap<>();
    }

    // We need add this constructor to work json deserializing
    @JsonCreator
    public PhoneBook(@JsonProperty("phoneBook") Map<String, String> name2number) {
        this.phoneBook = name2number;
    }

    // We need add this method to work json serializing
    public Map<String, String> getPhoneBook() {
        return phoneBook;
    }

    public PhoneBookStatus addEntry(String name, String number) {
        if (phoneBook.containsKey(name))
            return PhoneBookStatus.ERROR_NAME_EXIST;

        if (phoneBook.containsValue(number))
            return PhoneBookStatus.ERROR_NUMBER_EXIST;

        phoneBook.put(name, number);

        return PhoneBookStatus.OK;
    }

    public PhoneBookStatus delEntryByName(String name) {
        if (!phoneBook.containsKey(name))
            return PhoneBookStatus.ERROR_NAME_NOT_EXIST;

        phoneBook.remove(name);

        return PhoneBookStatus.OK;
    }

    public PhoneBookStatus delEntryByNumber(String number) {
        Optional<String> name = getNameByNumber(number);

        if (name.isEmpty())
            return PhoneBookStatus.ERROR_NUMBER_NOT_EXIST;

        phoneBook.remove(name.get(), number);

        return PhoneBookStatus.OK;
    }

    public Optional<String> getNumberByName(String name) {
        if (phoneBook.containsKey(name))
            return Optional.of(phoneBook.get(name));

        return Optional.empty();
    }

    public Optional<String> getNameByNumber(String number) {
        for (Entry<String, String> entry : phoneBook.entrySet()) {
            if (entry.getValue().equals(number))
                return Optional.of(entry.getKey());
        }

        return Optional.empty();
    }

    public PhoneBookStatus setNumberByName(String name, String newNumber) {
        if (!phoneBook.containsKey(name))
            return PhoneBookStatus.ERROR_NAME_NOT_EXIST;

        phoneBook.put(name, newNumber);

        return PhoneBookStatus.OK;
    }

    public PhoneBookStatus setNameByNumber(String newName, String number) {
        PhoneBookStatus status = delEntryByNumber(number);

        if (status.equals(PhoneBookStatus.OK))
            status = addEntry(newName, number);

        return status;
    }

    public void clear() {
        phoneBook.clear();
    }

    public boolean isEmpty() {
        return phoneBook.isEmpty();
    }

    @Override
    public String toString() {
        if (isEmpty())
            return "Phone book is empty.";

        return "Phone book:\n" +
                phoneBook.entrySet().stream()
                        .map(e -> e.getKey() + " -> " + e.getValue())
                        .collect(Collectors.joining("\n"));
    }

    public enum PhoneBookStatus {
        OK("OK."),
        ERROR_NAME_EXIST("This name already exist in phone book."),
        ERROR_NUMBER_EXIST("This number already exist in phone book."),
        ERROR_NAME_NOT_EXIST("This name doesn't exist in phone book."),
        ERROR_NUMBER_NOT_EXIST("This number doesn't exist in phone book.");

        private final String message;

        PhoneBookStatus(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return message;
        }
    }
}
