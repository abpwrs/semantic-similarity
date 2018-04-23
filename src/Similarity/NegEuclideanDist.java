package Similarity;

public class NegEuclideanDist implements SimilarityFunction {
    @Override
    public double calculateSimilarity() {
        return 0;
    }

    @Override
    public String getMethodName() {
        return "Negative Euclidean Distance Between Vectors";
    }
}
