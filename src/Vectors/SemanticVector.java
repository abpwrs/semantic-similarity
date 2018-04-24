package Vectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SemanticVector implements GenericVector {
    // Attributes
    private String base_word;
    private Double magnitude;
    private HashMap<String, Integer> related_words;
    private boolean magnitude_up_to_date = false;

    //Methods

    /**
     * @param main_word
     * @param dataset
     */
    public SemanticVector(String main_word, ArrayList<HashSet<String>> dataset) {
        this.base_word = main_word;
        this.related_words = new HashMap<>();
        for (HashSet<String> sentence : dataset) {
            if (sentence.contains(main_word)) {
                for (String word : sentence) {
                    if (!main_word.equals(word)) {
                        if (related_words.containsKey(word)) {
                            this.related_words.put(word, this.related_words.get(word) + 1);
                        } else {
                            this.related_words.put(word, 1);
                        }
                    }
                }
            }
        }
        this.updateMagnitude();
        this.magnitude_up_to_date = true;
    }

    /**
     * @return
     */
    @Override
    public double getMagnitude() {
        if (!magnitude_up_to_date) {
            this.updateMagnitude();
        }
        return this.magnitude;

    }

    /**
     * @return
     */
    @Override
    public HashMap<String, Integer> getVector() {
        return this.related_words;
    }

    /**
     * @return
     */
    @Override
    public String getWord() {
        return this.base_word;
    }

    /**
     *
     */
    private void updateMagnitude() {
        // this is standard n-dimensional vector magnitude
        this.magnitude = 0.0;
        for (Integer val : related_words.values()) {
            if (val != 0)
                this.magnitude += val * val;
        }
        this.magnitude = Math.sqrt(this.magnitude);

        this.magnitude_up_to_date = true;
    }

    /**
     * @param dataset
     */
    @Override
    public void update(ArrayList<HashSet<String>> dataset) {
        this.magnitude_up_to_date = false;
        //TODO: write a method to update the vector
    }


}
