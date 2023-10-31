package phone.book;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import phone.book.PhoneBook;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PhoneBookTest {
    private PhoneBook phoneBook;

    private void fillPhoneBook() {
        phoneBook.addEntry("Madelyn", "+9(240)088-68-54");
        phoneBook.addEntry("Zowie", "+6(082)235-65-00");
        phoneBook.addEntry("Lorrie", "+3(013)818-30-34");
        phoneBook.addEntry("Taylor", "+2(54)677-00-09");
        phoneBook.addEntry("Jewell", "+7(682)477-09-31");
        phoneBook.addEntry("Skylar", "+9(829)738-05-85");
        phoneBook.addEntry("Billie", "+7(611)823-54-40");
    }

    @BeforeEach
    void setUp() {
        phoneBook = new PhoneBook();
    }

    @AfterEach
    void tearDown() {
        phoneBook.clear();
    }

    @Test
    void addEntry() {
        assertAll(
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.addEntry("Madelyn", "+9(240)088-68-54")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.addEntry("Zowie", "+6(082)235-65-00")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.addEntry("Lorrie", "+3(013)818-30-34")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.ERROR_NUMBER_EXIST,
                        phoneBook.addEntry("Taylor", "+3(013)818-30-34")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.addEntry("Taylor", "+2(54)677-00-09")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.ERROR_NAME_EXIST,
                        phoneBook.addEntry("Taylor", "+7(682)477-09-31")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.addEntry("Jewell", "+7(682)477-09-31")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.addEntry("Skylar", "+9(829)738-05-85")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.ERROR_NAME_EXIST,
                        phoneBook.addEntry("Skylar", "+9(829)738-05-85")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.addEntry("Billie", "+7(611)823-54-40")
                )
        );
    }

    @Test
    void delEntryByName() {
        fillPhoneBook();

        assertAll(
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.delEntryByName("Madelyn")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.delEntryByName("Zowie")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.delEntryByName("Lorrie")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.ERROR_NAME_NOT_EXIST,
                        phoneBook.delEntryByName("Von")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.ERROR_NAME_NOT_EXIST,
                        phoneBook.delEntryByName("Lorrie")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.delEntryByName("Taylor")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.ERROR_NAME_NOT_EXIST,
                        phoneBook.delEntryByName("Michael")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.delEntryByName("Jewell")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.delEntryByName("Skylar")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.ERROR_NAME_NOT_EXIST,
                        phoneBook.delEntryByName("Kristopher")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.delEntryByName("Billie")
                ),
                () -> assertTrue(
                        phoneBook.isEmpty()
                )
        );
    }

    @Test
    void delEntryByNumber() {
        fillPhoneBook();

        assertAll(
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.delEntryByNumber("+9(240)088-68-54")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.delEntryByNumber("+6(082)235-65-00")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.delEntryByNumber("+3(013)818-30-34")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.ERROR_NUMBER_NOT_EXIST,
                        phoneBook.delEntryByNumber("+3(007)771-57-75")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.ERROR_NUMBER_NOT_EXIST,
                        phoneBook.delEntryByNumber("+3(013)818-30-34")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.delEntryByNumber("+2(54)677-00-09")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.ERROR_NUMBER_NOT_EXIST,
                        phoneBook.delEntryByNumber("+5(547)677-99-96")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.delEntryByNumber("+7(682)477-09-31")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.delEntryByNumber("+9(829)738-05-85")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.ERROR_NUMBER_NOT_EXIST,
                        phoneBook.delEntryByNumber("+6(969)177-43-77")
                ),
                () -> assertEquals(
                        PhoneBook.PhoneBookStatus.OK,
                        phoneBook.delEntryByNumber("+7(611)823-54-40")
                ),
                () -> assertTrue(
                        phoneBook.isEmpty()
                )
        );
    }

    @Test
    void getNumberByName() {
        fillPhoneBook();

        Optional<String> number;

        number = phoneBook.getNumberByName("Madelyn");
        assertTrue(number.isPresent() && number.get().equals("+9(240)088-68-54"));

        number = phoneBook.getNumberByName("Von");
        assertTrue(number.isEmpty());
    }

    @Test
    void getNameByNumber() {
        fillPhoneBook();

        Optional<String> name;

        name = phoneBook.getNameByNumber("+9(240)088-68-54");
        assertTrue(name.isPresent() && name.get().equals("Madelyn"));

        name = phoneBook.getNameByNumber("+3(007)771-57-75");
        assertTrue(name.isEmpty());
    }

    @Test
    void setNumberByName() {
        fillPhoneBook();

        PhoneBook.PhoneBookStatus status;

        status = phoneBook.setNumberByName("Madelyn", "+3(007)771-57-75");
        assertEquals(PhoneBook.PhoneBookStatus.OK, status);
        assertEquals(PhoneBook.PhoneBookStatus.OK, phoneBook.delEntryByNumber("+3(007)771-57-75"));

        status = phoneBook.setNumberByName("Von", "+3(007)971-50-85");
        assertEquals(PhoneBook.PhoneBookStatus.ERROR_NAME_NOT_EXIST, status);
    }

    @Test
    void setNameByNumber() {
        fillPhoneBook();

        PhoneBook.PhoneBookStatus status;

        status = phoneBook.setNameByNumber("Von", "+9(240)088-68-54");
        assertEquals(PhoneBook.PhoneBookStatus.OK, status);
        assertEquals(PhoneBook.PhoneBookStatus.OK, phoneBook.delEntryByName("Von"));

        status = phoneBook.setNameByNumber("Von", "+3(007)971-50-85");
        assertEquals(PhoneBook.PhoneBookStatus.ERROR_NUMBER_NOT_EXIST, status);
    }
}
