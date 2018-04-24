package Similarity;

import Vectors.SemanticVector;

public class NegEuclideanDist implements SimilarityFunction {
    @Override
    public double calculateSimilarity(SemanticVector main_vector, SemanticVector comp_vector) {
        return 0;
    }

    @Override
    public String getMethodName() {
        return "Negative Euclidean Distance Between Vectors";
    }
}
