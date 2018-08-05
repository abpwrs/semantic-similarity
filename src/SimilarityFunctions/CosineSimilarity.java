package SimilarityFunctions;

import Vectors.SemanticVector;

import java.util.Map;

public class CosineSimilarity extends SimilarityFunction {
    /**
     * @param main_vector
     * @param comp_vector
     * @return
     */
    @Override
    public double calculateSimilarity(SemanticVector main_vector, SemanticVector comp_vector) {
        Double sum = 0.0;

        if (main_vector.getMagnitude() == 0 || comp_vector.getMagnitude() == 0) {
            return sum;
        }
        for (Map.Entry<String, Double> entry : main_vector.getVector().entrySet()) {
            if (entry.getValue() != 0) {
                if (comp_vector.getVector().containsKey(entry.getKey()) && comp_vector.getVector().get(entry.getKey()) != 0) {
                    sum += entry.getValue() * comp_vector.getVector().get(entry.getKey());
                }
            }
        }
        return sum / Math.sqrt(main_vector.getMagnitude() * comp_vector.getMagnitude());
    }

    /**
     * @return
     */
    @Override
    public String getMethodName() {
        return "Cosine SimilarityFunctions";
    }

    /**
     * @return
     */
    @Override
    public Double getUnrelatedValue() {
        return 0.0;
    }
}