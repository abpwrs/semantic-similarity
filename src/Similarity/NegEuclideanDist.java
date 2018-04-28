package Similarity;

import Vectors.SemanticVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NegEuclideanDist implements SimilarityFunction {
    //TODO: BEN: Make this

    @Override
    public double calculateSimilarity(SemanticVector main_vector, SemanticVector comp_vector) {
        return 0;
    }

    @Override
    public String getMethodName() {
        return "Negative Euclidean Distance Between Vectors";
    }

    @Override
    public ArrayList<Map.Entry<String, Double>> getmax(HashMap<String, Double> relation, Integer J) {
        return null;
    }
}
