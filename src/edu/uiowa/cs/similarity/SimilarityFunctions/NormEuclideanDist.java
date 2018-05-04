package edu.uiowa.cs.similarity.SimilarityFunctions;

import edu.uiowa.cs.similarity.Vectors.SemanticVector;

/**
 *
 */
public class NormEuclideanDist extends SimilarityFunction {

    /**
     * @param main_vector
     * @param comp_vector
     * @return
     */
    @Override
    public double calculateSimilarity(SemanticVector main_vector, SemanticVector comp_vector) {
        // This may also work.....
        SemanticVector temp_main = main_vector;
        temp_main.normalizeBy(Math.sqrt(temp_main.getMagnitude()));
        SemanticVector temp_comp = comp_vector;
        temp_comp.normalizeBy(Math.sqrt(temp_comp.getMagnitude()));
        SimilarityFunction temp = new NegEuclideanDist();
        return temp.calculateSimilarity(temp_main, temp_comp);


//        Double sum = 0.0;
//        if (main_vector.getMagnitude() == 0 || comp_vector.getMagnitude() == 0) {
//            return sum;
//        }
//        for (Map.Entry<String, Double> entry : main_vector.getVector().entrySet()) {
//            if (entry.getValue() != 0) {
//                if (comp_vector.getVector().containsKey(entry.getKey()) && comp_vector.getVector().get(entry.getKey()) != 0) {
//                    sum += Math.pow(
//                            entry.getValue() / Math.sqrt(main_vector.getMagnitude()) -
//                                    comp_vector.getVector().get(entry.getKey()) / Math.sqrt(comp_vector.getMagnitude()),
//                            2);
//                }
//            }
//        }
//        return -1 * Math.sqrt(sum);
    }

    /**
     * @return
     */
    @Override
    public String getMethodName() {
        return "Negative Euclidean Distance between Normalized edu.uiowa.cs.similarity.Vectors";
    }

    /**
     * @return
     */
    @Override
    public Double getUnrelatedValue() {
        return Double.NEGATIVE_INFINITY;
    }

}
