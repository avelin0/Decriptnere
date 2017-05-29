package com.example.bruno.dekriptnere;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre on 28/05/17.
 */

public class EstimateKeySizeTask implements Runnable {
    int tempKeyLength;
    int cipherLength;
    Kasiski kas;
    int[] nListOfOptions;
    public  EstimateKeySizeTask(int tempKeyLength, int cypherLength, Kasiski kas, int[] nListOfOptions){
        this.tempKeyLength = tempKeyLength;
        this.cipherLength = cypherLength;
        this.kas = kas;
        this.nListOfOptions = nListOfOptions;
    }

    @Override
    public void run() {
        List<Integer> nListOfIndexSubstring;
        List<Integer> nListOfDifferenceBetweenIndexes = new ArrayList<Integer>();
        int i,j;
        for (i = 0; i <= cipherLength - tempKeyLength; i++) {
            nListOfIndexSubstring = kas.getListOfIndexesOfSubstring(i, tempKeyLength);
            nListOfDifferenceBetweenIndexes.clear();
            for (j = 1; j < nListOfIndexSubstring.size(); j++) {
                nListOfDifferenceBetweenIndexes.add(nListOfIndexSubstring.get(j) - nListOfIndexSubstring.get(j - 1));
            }
            synchronized (nListOfOptions) {
                nListOfOptions[kas.CalculateGdc(nListOfDifferenceBetweenIndexes)]++;
            }
        }
    }
}
