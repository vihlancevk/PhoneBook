import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@JsonIncludeProperties("name2number")
public class PhoneBook {
    private final Map<String, String> name2number;

    public PhoneBook() {
        this.name2number = new HashMap<>();
    }

    // We need add this constructor to work json deserializing
    @JsonCreator
    public PhoneBook(@JsonProperty("name2number") Map<String, String> name2number) {
        this.name2number = name2number;
    }

    // We need add this method to work json serializing
    public Map<String, String> getName2number() {
        return name2number;
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
        return name2number.entrySet().stream()
                .map(e -> e.getKey() + " -> " + e.getValue())
                .collect(Collectors.joining("\n"));
    }

    public enum PhoneBookStatus {
        OK,
        ERROR_NAME_EXIST,
        ERROR_NUMBER_EXIST,
        ERROR_NAME_NOT_EXIST,
        ERROR_NUMBER_NOT_EXIST,
    }
}
