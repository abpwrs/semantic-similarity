package edu.uiowa.cs.similarity.Vectors;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public interface GenericVector {

    /**
     * @return
     */
    double getMagnitude();

    /**
     * @return
     */
    HashMap<String, Double> getVector();

    /**
     * @return
     */
    String getWord();

    /**
     * @param dataset
     */
    void update(ArrayList<ArrayList<String>> dataset);

    /**
     * @return
     */
    boolean isEmpty();

}
