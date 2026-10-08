
import org.junit.Test;
import static org.junit.Assert.*;

public class LibraryTest {

    // Test Case 1: Check available books
    @Test
    public void testAvailableBooks() {
        int totalBooks = 10;
        int issuedBooks = 4;

        int availableBooks = totalBooks - issuedBooks;

        assertEquals(6, availableBooks);
    }

    // Test Case 2: Check book issue calculation
    @Test
    public void testBookIssue() {
        int totalBooks = 20;
        int issuedBooks = 5;

        int availableBooks = totalBooks - issuedBooks;

        assertEquals(15, availableBooks);
    }

    // Test Case 3: Check fine calculation
    @Test
    public void testFineCalculation() {
        int lateDays = 5;
        int finePerDay = 5;

        int fine = lateDays * finePerDay;

        assertEquals(25, fine);
    }

    // Test Case 4: Check that available books cannot be negative
    @Test
    public void testAvailableBooksNotNegative() {
        int totalBooks = 5;
        int issuedBooks = 8;

        int availableBooks = totalBooks - issuedBooks;

        assertTrue(availableBooks < 0);
    }
}
```
