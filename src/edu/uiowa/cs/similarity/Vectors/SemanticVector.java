package edu.uiowa.cs.similarity.Vectors;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class SemanticVector implements GenericVector {

    // Attributes
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private String base_word;
    /**
     *
     */
    private Double magnitude;
    /**
     *
     */
    private HashMap<String, Double> related_words;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // Constructors
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @param main_word
     * @param dataset
     */
    public SemanticVector(String main_word, ArrayList<ArrayList<String>> dataset) {
        this.base_word = main_word;
        this.related_words = new HashMap<String, Double>();
        this.update(dataset);
        this.updateMagnitude();
    }

    /**
     *
     */
    public SemanticVector() {
        related_words = new HashMap<String, Double>();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // UTILITY FUNCTIONS (i.e. private functions)
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     *
     */
    private void updateMagnitude() {
        // this is standard n-dimensional vector magnitude without the square root
        this.magnitude = 0.0;
        for (Double val : related_words.values()) {
            if (val != 0) {
                this.magnitude += val * val;
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // Getters
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
    public HashMap<String, Double> getVector() {
        return this.related_words;
    }


    /**
     * @return
     */
    @Override
    public String getWord() {
        return this.base_word;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // Overloaded Update Methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
                            this.related_words.put(word, 1.0);
                        }
                    }
                }
            }
        }
        this.updateMagnitude();
    }

    /**
     * @param rhs
     */
    public void update(SemanticVector rhs) {
        for (Map.Entry<String, Double> word : rhs.getVector().entrySet()) {
            if (related_words.containsKey(word.getKey())) {
                related_words.put(word.getKey(), related_words.get(word.getKey()) + word.getValue());
            } else {
                related_words.put(word.getKey(), word.getValue());
            }
        }
        this.updateMagnitude();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // Other useful functions
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *
     */
    public void print() {
        System.out.println("(" + base_word + ":" + magnitude.toString() + ")" + ":" + related_words.toString());
    }

    /**
     * @param value
     */
    public void normalizeBy(Double value) {
        for (Map.Entry<String, Double> temp : related_words.entrySet()) {
            related_words.put(temp.getKey(), temp.getValue() / value);
        }
        this.updateMagnitude();
    }

    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        return related_words.isEmpty();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
