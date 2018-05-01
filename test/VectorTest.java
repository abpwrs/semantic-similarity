import Similarity.NegEuclideanDist;
import Vectors.GenericVector;
import Vectors.SemanticVector;
import org.junit.Test;

import java.util.ArrayList;

<<<<<<< HEAD
import static org.junit.Assert.assertTrue;
=======
import DB.FileParser;
import DB.WordDB;
import Similarity.CosineSimilarity;

import opennlp.tools.stemmer.PorterStemmer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import static org.junit.Assert.*;
>>>>>>> origin/ben_backup

public class VectorTest {
    public VectorTest() {
    }

    @Test
    public void testIsEmpty() {
<<<<<<< HEAD
        GenericVector test_vector = new SemanticVector("TEST", new ArrayList<>());
        assertTrue(test_vector.isEmpty());
=======
        GenericVector emptySemanticVector = new SemanticVector("TEST", new ArrayList<>());
        assertTrue(emptySemanticVector.isEmpty());
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
>>>>>>> origin/ben_backup
    }
}
