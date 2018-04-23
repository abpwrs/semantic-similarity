package Vectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SemanticVector implements GenericVector {
    // Attributes
    String main_word;
    HashMap<String, Integer> relatedWords;

    //Methods
    public SemanticVector(String main_word, ArrayList<HashSet<String>> dataset) {
        relatedWords = new HashMap<>();
        for (HashSet<String> sentence : dataset) {
            if (sentence.contains(main_word)) {
                for (String word : sentence) {
                    if (!main_word.equals(word)) {
                        if (relatedWords.containsKey(word)) {
                            relatedWords.put(word, relatedWords.get(word) + 1);
                        } else {
                            relatedWords.put(word, 1);
                        }
                    }
                }
            }
        }
    }

    @Override
    public double getMagnitude() {
        return 0;

    }

    public HashMap<String, Integer> getVector() {
        return relatedWords;
    }

    public String getWord() {
        return main_word;
    }


}
