package Similarity;

import Vectors.SemanticVector;

import java.util.Map;

public class NormEuclideanDist extends SimilarityFunction {

    @Override
    public double calculateSimilarity(SemanticVector main_vector, SemanticVector comp_vector) {
        Double sum = 0.0;
        if (main_vector.getMagnitude() == 0 || comp_vector.getMagnitude() == 0) {
            return sum;
        }
        for (Map.Entry<String, Integer> entry : main_vector.getVector().entrySet()) {
            if (entry.getValue() != 0) {
                if (comp_vector.getVector().containsKey(entry.getKey()) && comp_vector.getVector().get(entry.getKey()) != 0) {
                    sum +=  Math.pow(
                            entry.getValue() / main_vector.getMagnitude() -
                                    comp_vector.getVector().get(entry.getKey()) / comp_vector.getMagnitude(),
                            2);
                }
            }
        }
        return -1 * Math.sqrt(sum);
    }

    @Override
    public String getMethodName() {
        return "negative euclidean distance between norms";
    }

    @Override
    public Double getUnrelatedValue() {
        //TODO: ALEX: Figure out what the unrelated value is....
        return null;
    }

}
