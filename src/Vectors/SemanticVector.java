package Vectors;


import java.util.ArrayList;
import java.util.HashMap;

public class SemanticVector implements GenericVector {
    // Attributes
    private String base_word;
    private Double magnitude;
    private HashMap<String, Integer> related_words;

    //Methods
    //TODO: BEN: Make a bad vector

    /**
     * @param main_word
     * @param dataset
     */
    public SemanticVector(String main_word, ArrayList<ArrayList<String>> dataset) {
        this.base_word = main_word;
        this.related_words = new HashMap<>();
        this.update(dataset);
        this.updateMagnitude();
    }

    /**
     * @return
     */
    @Override
    public double getMagnitude() {
        return this.magnitude;
    }

    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        return related_words.isEmpty();
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
        // this is standard n-dimensional vector magnitude without the square root
        this.magnitude = 0.0;
        for (Integer val : related_words.values()) {
            if (val != 0) {
                this.magnitude += val * val;
            }
        }
    }

    /**
     * @param dataset
     */
    @Override
    public void update(ArrayList<ArrayList<String>> dataset) {
        for (ArrayList<String> sentence : dataset) {
            if (sentence.contains(this.base_word)) {
                for (String word : sentence) {
                    if (!this.base_word.equals(word)) {
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
    }
}
