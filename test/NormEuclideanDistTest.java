import edu.uiowa.cs.similarity.DB.WordDB;
import edu.uiowa.cs.similarity.SimilarityFunctions.NegEuclideanDist;
import edu.uiowa.cs.similarity.SimilarityFunctions.NormEuclideanDist;
import edu.uiowa.cs.similarity.Vectors.SemanticVector;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;


public class NormEuclideanDistTest {

    @Test
    public void normTestEasy() {
        WordDB testDB = new WordDB();
        testDB.index("src/data/junit_Easy.txt");

        SemanticVector[] vectorArray = new SemanticVector[100];
        int i = 0;
        for (SemanticVector vector : testDB.getVectors()) {
            vectorArray[i] = vector;
            //vector.print();
            i++;
        }

        ArrayList<Map.Entry<String,Double>> result = testDB.TopJ(vectorArray[3].getWord(), 8, new NormEuclideanDist());
        //System.out.println(result);
        assertEquals(result.get(0).getValue(), -1.16774, .00001);
        assertEquals(result.get(1).getValue(), -1.20482, .00001);
        assertEquals(result.get(5).getValue(), -1.24255, .00001);
    }

    @Test
    public void normTestMedium() {
        //Calling top j for top 2 on "project" vectorArray[3].getWord() with negEuc function
        WordDB testDB = new WordDB();
        testDB.index("src/data/vector_test.txt");

        SemanticVector[] vectorArray = new SemanticVector[100];
        int i = 0;
        for (SemanticVector vector : testDB.getVectors()) {
            vectorArray[i] = vector;
            //vector.print();
            i++;
        }

        ArrayList<Map.Entry<String,Double>> result = testDB.TopJ(vectorArray[12].getWord(), 8, new NegEuclideanDist());
        //System.out.println(result);
        assertEquals(result.get(0).getValue(), -4.12310, .00001);
        assertEquals(result.get(2).getValue(), -4.35889, .00001);
        assertEquals(result.get(4).getValue(), -4.69041, .00001);
    }

    @Test
    public void normTestHard() {
        //Calling top j for top 2 on "project" vectorArray[3].getWord() with negEuc function
        WordDB testDB = new WordDB();
        testDB.index("src/data/junit_Hard.txt");

        SemanticVector[] vectorArray = new SemanticVector[400];
        int i = 0;
        for (SemanticVector vector : testDB.getVectors()) {
            vectorArray[i] = vector;
            //if(i<10){vector.print();}
            i++;
        }

        ArrayList<Map.Entry<String,Double>> result = testDB.TopJ(vectorArray[3].getWord(), 8, new NegEuclideanDist());
        //System.out.println(result);
        assertEquals(result.get(0).getValue(), -21.86321, .00001);
        assertEquals(result.get(1).getValue(), -21.86321, .00001);
        assertEquals(result.get(2).getValue(), -21.86321, .00001);
        assertEquals(result.get(3).getValue(), -21.86321, .00001);
    }
}