package entry;

public class Entry {
    private final int entryId;
    private final int userId;
    private final String name;
    private final String number;

    public Entry(int userId, String name, String number) {
        this.entryId = -1;
        this.userId = userId;
        this.name = name;
        this.number = number;
    }

    public Entry(int entryId, int userId, String name, String number) {
        this.entryId = entryId;
        this.userId = userId;
        this.name = name;
        this.number = number;
    }

    public int getEntryId() {
        return entryId;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
