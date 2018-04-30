import Vectors.GenericVector;
import Vectors.SemanticVector;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class VectorTest {
    public VectorTest() {
    }

    @Test
    public void testIsEmpty() {
        GenericVector test_vector = new SemanticVector("TEST", new ArrayList<>());
        assertTrue(test_vector.isEmpty());
    }
}
