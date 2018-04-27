import Vectors.GenericVector;
import Vectors.SemanticVector;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

public class VectorTest {
    public VectorTest() {
    }

    @Test
    public void testIsEmpty() {
        GenericVector wordDB = new SemanticVector("TEST", new ArrayList<>());
        assertTrue(wordDB.isEmpty());
    }
}
