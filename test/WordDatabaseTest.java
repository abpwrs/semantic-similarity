import DB.WordDB;
import org.junit.Test;

import static org.junit.Assert.*;


public class WordDatabaseTest {
    public WordDatabaseTest() {
    }

    @Test
    public void testIsEmpty() {
        WordDB wordDB = new WordDB();
        assertTrue(wordDB.isEmpty());
    }
}
