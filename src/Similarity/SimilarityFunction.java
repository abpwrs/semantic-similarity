package Similarity;

import Vectors.SemanticVector;

public interface SimilarityFunction {
    //TODO: Figure out what functions could be added to this interface
    public double calculateSimilarity(SemanticVector main_vector, SemanticVector comp_vector);

    public String getMethodName();
}
