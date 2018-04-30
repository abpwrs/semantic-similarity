package Similarity;

import Vectors.SemanticVector;

public class NormEuclideanDist extends SimilarityFunction {
    //TODO: BEN: Make this

    @Override
    public double calculateSimilarity(SemanticVector main_vector, SemanticVector comp_vector) {
        return 0;
    }

    @Override
    public String getMethodName() {
        return "Negative Euclidean Distance Between Normalized Vectors";
    }

    @Override
    public Double getUnrelatedValue() {
        //TODO: ALEX: Figure out what the unrelated value is....
        return null;
    }

}
