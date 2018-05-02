package Vectors;

import java.util.ArrayList;
import java.util.HashMap;

public interface GenericVector {

    double getMagnitude(/* idk if this needs parameters*/);

    HashMap<String, Double> getVector();

    String getWord();

    void update(ArrayList<ArrayList<String>> dataset);

    boolean isEmpty();

}
