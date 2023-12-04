package types;

import java.io.Serializable;
import java.util.ArrayList;

public class PrimeResults implements Serializable {
    public PrimeResults(ArrayList<Integer> ownResults, ArrayList<Integer> remoteResults, long time){
        this.ownResults = ownResults.stream().mapToInt(Integer::intValue).toArray();
        this.remoteResults = remoteResults.stream().mapToInt(Integer::intValue).toArray();
        this.time = time;
    }
    public int[] ownResults;
    public int [] remoteResults;
    public long time;
}
