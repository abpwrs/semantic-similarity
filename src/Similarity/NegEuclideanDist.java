package Similarity;

import Vectors.SemanticVector;

import java.util.*;

public class NegEuclideanDist implements SimilarityFunction {
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
        return Math.sqrt(sum);
    }

    @Override
    public String getMethodName() {
        return "Negative Euclidean Distance Between Vectors";
    }

    @Override
    public ArrayList<Map.Entry<String, Double>> getmax(HashMap<String, Double> relation, Integer J) {
        //?? TODO: BEN: Isn't getmax simFunction independent? Calculated the same for any simFunc right?
        ArrayList<Map.Entry<String, Double>> ret = new ArrayList<>();
        boolean sentinel = true;
        for (int i = 0; i < J && sentinel; i++) {
            try {
                String max = Collections.max(relation.entrySet(), Map.Entry.comparingByValue()).getKey();
                ret.add(new AbstractMap.SimpleEntry<String, Double>(max, relation.get(max)));
                relation.remove(max);
            } catch (NoSuchElementException e) {
                System.out.println("Not enough related elements to compare.\nReturning all related elements.\n");
                sentinel = false;
            }
        }
        return ret;
    }
}
