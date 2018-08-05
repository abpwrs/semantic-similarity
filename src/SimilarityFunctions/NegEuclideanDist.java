package SimilarityFunctions;

import Vectors.SemanticVector;

import java.util.Map;

/**
 *
 */
public class NegEuclideanDist extends SimilarityFunction {

    /**
     * @param main_vector
     * @param comp_vector
     * @return
     */
    @Override
    public double calculateSimilarity(SemanticVector main_vector, SemanticVector comp_vector) {
        Double sum = 0.0;
        if (comp_vector.getMagnitude() == 0 || main_vector.getMagnitude() == 0) {
            for (Map.Entry<String, Double> comp_entry : comp_vector.getVector().entrySet()) {
                if (!main_vector.getVector().containsKey(comp_entry.getKey())) {
                    sum += (comp_entry.getValue() * comp_entry.getValue());
                }
            }
            for (Map.Entry<String, Double> main_entry : main_vector.getVector().entrySet()) {
                if (!main_vector.getVector().containsKey(main_entry.getKey())) {
                    sum += (main_entry.getValue() * main_entry.getValue());
                }
            }
        } else {
            for (Map.Entry<String, Double> entry : main_vector.getVector().entrySet()) {
                if (entry.getValue() != 0) {
                    //ERROR
                    if (comp_vector.getVector().containsKey(entry.getKey()) && comp_vector.getVector().get(entry.getKey()) != 0) {
                        sum += (entry.getValue() - comp_vector.getVector().get(entry.getKey())) *
                                (entry.getValue() - comp_vector.getVector().get(entry.getKey()));
                    } else {
                        sum += (entry.getValue() * entry.getValue());
                    }
                }
            }

            for (Map.Entry<String, Double> comp_entry : comp_vector.getVector().entrySet()) {
                if (!main_vector.getVector().containsKey(comp_entry.getKey())) {
                    sum += (comp_entry.getValue() * comp_entry.getValue());
                }
            }
        }

        return -1 * Math.sqrt(sum);
    }

    /**
     * @return
     */
    @Override
    public String getMethodName() {
        return "Non-Normalized Negative Euclidean Distance";
    }

    /**
     * @return
     */
    @Override
    public Double getUnrelatedValue() {
        return Double.NEGATIVE_INFINITY;
    }


}
