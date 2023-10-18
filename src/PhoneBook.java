import java.util.*;

public class PhoneBook {
    private final Map<String, List<PhoneBookEntry>> name2entries;

    public PhoneBook() {
        this.name2entries = new HashMap<>();
    }

    public void addEntry(String name, String number) {
        if (!name2entries.containsKey(name))
            name2entries.put(
                name, new ArrayList<>(
                        Arrays.asList(new PhoneBookEntry(name, number))
                )
            );
        else
            name2entries.get(name).add(new PhoneBookEntry(name, number));
    }

    public Optional<List<PhoneBookEntry>> getNumbersByName(String name) {
        if (!name2entries.containsKey(name))
            return Optional.empty();

        return Optional.of(name2entries.get(name));
    }

    @Override
    public String toString() {
        StringBuilder resultedString = new StringBuilder();

        for (String name : name2entries.keySet()) {
            resultedString.append(name).append(" ").append("->");
            for (PhoneBookEntry entry : name2entries.get(name))
                resultedString.append(" ").append(entry).append(",");
            resultedString.append("\n");
        }


        return resultedString.toString();
    }

    public static class PhoneBookEntry {
        private final String name;
        private final String number;

        public PhoneBookEntry(String name, String number) {
            this.name = name;
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public String getNumber() {
            return number;
        }

        @Override
        public String toString() {
            return number;
        }
    }

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();

        phoneBook.addEntry("Костя", "+7(9xx)xxx-18-xx");
        phoneBook.addEntry("Андрей", "+7(9**)***-68-**");
        phoneBook.addEntry("Алечка", "+7(9**)***-03-**");
        phoneBook.addEntry("Андрей", "+7(9xx)xxx-18-xx");

        System.out.println(phoneBook);
    }
}
