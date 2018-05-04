package edu.uiowa.cs.similarity.SimilarityFunctions;

import edu.uiowa.cs.similarity.Vectors.SemanticVector;

import java.util.*;

public abstract class SimilarityFunction {
    abstract public double calculateSimilarity(SemanticVector main_vector, SemanticVector comp_vector);

    abstract public String getMethodName();

    abstract public Double getUnrelatedValue();

    public ArrayList<Map.Entry<String, Double>> getMostRelated(HashMap<String, Double> relation, Integer J) {
        ArrayList<Map.Entry<String, Double>> ret = new ArrayList<>();
        boolean sentinel = true;
        for (int i = 0; i < J && sentinel; i++) {
            try {
                String max = Collections.max(relation.entrySet(), Map.Entry.comparingByValue()).getKey();
                ret.add(new AbstractMap.SimpleEntry<String, Double>(max, relation.get(max)));
                relation.remove(max);
            } catch (NoSuchElementException e) {
                // TODO: modify WordDB to return ccompletly unrelated values so that we can delete this line
                // System.out.println("Not enough related elements to compare.\nReturning all related elements.\n");
                sentinel = false;
            }
        }
        return ret;
    }


}
