package Similarity;

import Vectors.SemanticVector;

import java.util.Map;

public class NegEuclideanDist extends SimilarityFunction {
    //TODO: BEN: Write JUnit tests.

    @Override
    public double calculateSimilarity(SemanticVector main_vector, SemanticVector comp_vector) {
        Double sum = 0.0;
        if (main_vector.getMagnitude() == 0 || comp_vector.getMagnitude() == 0) {
            return sum;
        }
        for (Map.Entry<String, Integer> entry : main_vector.getVector().entrySet()) {
            if (entry.getValue() != 0) {
                if (comp_vector.getVector().containsKey(entry.getKey())) {
                    sum += (entry.getValue() - comp_vector.getVector().get(entry.getKey())) *
                            (entry.getValue() - comp_vector.getVector().get(entry.getKey()));
                }
            }
        }
        return -1 * Math.sqrt(sum);
    }

    @Override
    public String getMethodName() {
        return "Negative Euclidean Distance Between Vectors";
    }


}
