import org.junit.Test;
import static org.junit.Assert.*;

public class LibraryTest {

    @Test
    public void basicTest() {
        int books = 10;
        int issued = 4;

        int available = books - issued;

        assertEquals(6, available);
    }
}