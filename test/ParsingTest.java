import edu.uiowa.cs.similarity.DB.FileParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ParsingTest {
    public ParsingTest() {
    }

    @Test
    public void invalidFile() {
        assertEquals(null, FileParser.parse("NOT_A_REAL_FILE"));
    }
}
