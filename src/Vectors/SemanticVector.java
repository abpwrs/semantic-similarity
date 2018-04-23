package Vectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SemanticVector implements GenericVector {
    String word;
    HashMap<String, Integer> temp;

    @Override
    public double getMagnitude() {
        return 0;

    }

    public HashMap<String, Integer> getVector() {
        return temp;
    }

    public SemanticVector(String word, ArrayList<HashSet<String>> dataset) {
        temp = new HashMap<>();
        for (HashSet<String> sentence2 : dataset) {
            if (sentence2.contains(word)) {
                for (String word2 : sentence2) {
                    if (!word.equals(word2)) {
                        if (temp.containsKey(word2)) {
                            temp.put(word2, temp.get(word2) + 1);
                        } else {
                            temp.put(word2, 1);
                        }
                    }
                }
            }
        }
    }

}
