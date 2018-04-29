package Vectors;

import java.util.ArrayList;
import java.util.HashMap;

public interface GenericVector {
    //TODO: Figure out what functions could be added to this interface
    double getMagnitude(/* idk if this needs parameters*/);

    HashMap<String, Integer> getVector();

    String getWord();

    void update(ArrayList<ArrayList<String>> dataset);

    public boolean isEmpty();

}
