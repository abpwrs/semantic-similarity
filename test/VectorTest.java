import DB.WordDB;
import Similarity.NegEuclideanDist;
import Vectors.GenericVector;
import Vectors.SemanticVector;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class VectorTest {
    public VectorTest() {
    }

    @Test
    public void testIsEmpty() {
        GenericVector test_vector = new SemanticVector("TEST", new ArrayList<>());
        assertTrue(test_vector.isEmpty());


    }

    @Test
    public void negEucDist(){
        //Calling top j for top 2 on "project" vectorArray[3].getWord() with negEuc function
        WordDB testDB = new WordDB();
        testDB.index("src/data/negEuc_test.txt");

        SemanticVector[] vectorArray = new SemanticVector[20];
        int i = 0;
        for (SemanticVector vector : testDB.getVectors()) {
            vectorArray[i] = vector;
            vector.print();
            i++;
        }

        ArrayList<Map.Entry<String,Double>> result = testDB.TopJ(vectorArray[3].getWord(), 8, new NegEuclideanDist());
        System.out.println(result);
        //assertTrue();
    }
}
