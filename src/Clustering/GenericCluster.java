package Clustering;

import java.util.HashMap;

public interface GenericCluster {
    // TODO: Figure out what functions could be added to this interface
    // TODO: Figure out what a cluster is

    // I don't know how this is going to be stored internally
    void print();

    // I think this is just the average of values set
    Double findCentroid(HashMap<String,Double> map);
}
