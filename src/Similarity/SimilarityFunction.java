package Similarity;

import Vectors.SemanticVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface SimilarityFunction {
    //TODO: Figure out what functions could be added to this interface
    double calculateSimilarity(SemanticVector main_vector, SemanticVector comp_vector);

    String getMethodName();

    ArrayList<Map.Entry<String, Double>> getmax(HashMap<String, Double> relation, Integer J);
}
