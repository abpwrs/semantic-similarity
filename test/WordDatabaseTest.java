import edu.uiowa.cs.similarity.DB.WordDB;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class WordDatabaseTest {
    public WordDatabaseTest() {
    }

    @Test
    public void testIsEmpty() {
        WordDB wordDB = new WordDB();
        assertTrue(wordDB.isEmpty());
    }
}
