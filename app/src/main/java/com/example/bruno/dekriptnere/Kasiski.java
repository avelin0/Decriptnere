package com.example.bruno.dekriptnere;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinivice on 18/04/2017.
 */

public class Kasiski {
    public String cipher;
    private String plainText = "";
    private String key = "";
    private String CriptoLanguage = "";
    private int keyLength = 0;
    private List<List<Float>> matrixHistogramShifted = new ArrayList<List<Float>>();


    public Kasiski(String cipher, List<Dictionaries> languages) {
        this.cipher = cipher;
        beginEstimatingKeySize();
        estimateKeyBasedOnLanguage(languages);
        decriptVigenere();
    }

    public String getKey() {
        return key;
    }

    public String getPlainText() {
        return plainText;
    }

    public int getKeyLength() {
        return keyLength;
    }

    public String getCriptoLanguage() {
        return CriptoLanguage;
    }


    void beginEstimatingKeySize() {
        int[] nListOfOptions = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int i, j;
        int tempKeyLength;
        List<Integer> nListOfIndexSubstring;
//        list of differences between indexes
        List<Integer> nListOfDifferenceBetweenIndexes = new ArrayList<Integer>();

        //TODO: CAN ADD THREADS HERE
//        The key length was limited
        for (tempKeyLength = 3; tempKeyLength <= 21; tempKeyLength++) {
            for (i = 0; i <= this.cipher.length() - tempKeyLength; i++) {
                nListOfIndexSubstring = getListOfIndexesOfSubstring(i, tempKeyLength);
                nListOfDifferenceBetweenIndexes.clear();
                for (j = 1; j < nListOfIndexSubstring.size(); j++) {
                    nListOfDifferenceBetweenIndexes.add(nListOfIndexSubstring.get(j) - nListOfIndexSubstring.get(j - 1));
                }
                nListOfOptions[this.CalculateGdc(nListOfDifferenceBetweenIndexes)]++;
            }
        }

//        TODO:  do a function here
        int temp = nListOfOptions[1];
        int kenLength = 2;
        for ( i = 2; i <= 20; i++){
            if(nListOfOptions[i] > temp) {
                temp = nListOfOptions[i];
                kenLength = i;
            }
        }
//        TODO: get here?
        this.keyLength = kenLength;

    }

    void estimateKeyBasedOnLanguage(List<Dictionaries> languages) {
        this.getHistogramShiftedWithFrequencies();
        List<List<OffsetError>> matrixOffsetError = new ArrayList<List<OffsetError>>(this.keyLength);
        //  majority is an index to choose appropriate language
        List<Integer> majority = new ArrayList<Integer>();
        //  quantity of languages. English, Spanish, Portuguese by default
        for(int i = 0; i < languages.size(); i++) {
            majority.add(0);
        }

        //  fill in tuple mOffset mError in matrixOFssetError. THe length of each element is key length
        for(int i = 0; i < this.keyLength; i++) {
            matrixOffsetError.add(new ArrayList<OffsetError>());
        }

        //TODO: THREAD HERE??
//        runs through the matrix  to choose the right language
        for(int i = 0; i < this.keyLength; i++) {
            float error = Float.POSITIVE_INFINITY;
            int counter = -1;
//            TODO: Ver getListOfMinErrorForLanguage
            matrixOffsetError.set(i, getListOfMinErrorForLanguage(languages, i));

//            TODO: understand this loop
            for(int j = 0; j < languages.size(); j++) {
                if(matrixOffsetError.get(i).get(j).getmError()< error) {
                    error = matrixOffsetError.get(i).get(j).getmError();
                    counter = j;
                }
            }


            majority.set(counter, majority.get(counter) + 1);
        }

        int champion = 0;
        for(int i = 1; i < majority.size(); i++) {
            if(majority.get(i) > majority.get(champion)) {
                champion = i;
            }
        }

//        set champion language
        this.CriptoLanguage = languages.get(champion).getLanguage();
//        fill in string key with key char s
//        TODO: Thread HERE ??
        for(int i = 0; i < this.keyLength; i++) {
            char elementKeyLetter;
            elementKeyLetter = (char) ('A' + matrixOffsetError.get(i).get(champion).getmOffset());
            this.key += elementKeyLetter;
        }
    }



//tools
    private List<Integer> getListOfIndexesOfSubstring(int indexBeginning, int indexOffset) {
        //        get a substring between indexBeginning and indexEnd from cipher
        int indexEnd = indexBeginning + indexOffset;
        //        get temporarily the index of the substring in all sentence in each iteration
        String mSubString = this.cipher.substring(indexBeginning, indexEnd);
        int tempIndex = this.cipher.indexOf(mSubString, indexBeginning);
        List<Integer> ListOfIndexes = new ArrayList<Integer>();

        while(tempIndex >= 0){
            ListOfIndexes.add(tempIndex);
            tempIndex = this.cipher.indexOf(mSubString, tempIndex + 1);
        }

        return ListOfIndexes;
    }

    private int CalculateGdc(List<Integer> numbers) {
        int numberA, numberB, numberTemp;

        if(numbers.size() < 1) {
            return 0;
        }

        int numberResult = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {
            numberA = numberResult;
            numberB = numbers.get(i);

            while(numberB > 0) {
                numberTemp = numberB;
                numberB = numberA % numberB;
                numberA = numberTemp;
            }
            numberResult = numberA;
        }

        if (numberResult > 20){
            return 0;
        }
        return numberResult;
    }


    private List<Float> calculateFrequency(int numberOffset) {
        List<Integer> listLettersQuantity = new ArrayList<Integer>();
        List<Float> listLettersFrequency = new ArrayList<Float>(26);
        int mCounter;
        int letterQuantityTotal = 0;

//        Initialize Lists with 0
        for(int i = 0; i < 26; i++) {
            listLettersQuantity.add(0);
            listLettersFrequency.add(0.0f);
        }

//        Count how often appear each letter
//        TODO: THREAD HERE ?
        if (!((keyLength < 1) || (numberOffset < 0) || (numberOffset >= this.keyLength))) {
            for(int i = 0; (i * this.keyLength + numberOffset) < this.cipher.length(); i++) {
                mCounter = listLettersQuantity.get(this.cipher.charAt(i * this.keyLength + numberOffset) - 'A');
                listLettersQuantity.set(this.cipher.charAt(i * this.keyLength + numberOffset) - 'A', mCounter + 1);
                letterQuantityTotal++;
            }
        }

//        Assign  values to respective Lists
        for(int i = 0; i < 26; i++){
            mCounter = listLettersQuantity.get(i);
            listLettersFrequency.set(i, (float) mCounter/letterQuantityTotal);
        }

        return listLettersFrequency;
    }

    private void getHistogramShiftedWithFrequencies() {
        //TODO: My guess is do not need thread here because we have just 13 assigments, each assigment with an array of length 26 already assigned
        for(int i = 0; i < this.keyLength; i++) {
            this.matrixHistogramShifted.add(this.calculateFrequency(i));
        }
    }


    private float calculateOffsetError(Dictionaries language, int position, int offset) {
        float offsetError = 0;

//        go through matrix Histogram and language frequency estimating mError
        for(int i = 0; i < 26; i++) {
            float value1 = this.matrixHistogramShifted.get(position).get((i+offset) % 26) - language.getFrequencies().get(i);
            float value2 = language.getFrequencies().get(i);
            offsetError += ( value1 * value1 ) / value2 ;
        }

        return offsetError;
    }

    private OffsetError calculateMinimumOffsetError(Dictionaries language, int position) {
        int initialMinOffsetError = 0;
        float minOffsetError = this.calculateOffsetError(language, position, 0);
        float offsetError;

//        search and set the minimum
        for (int i = 1; i < 26; i++) {
            offsetError = this.calculateOffsetError(language, position, i);
            if(offsetError < minOffsetError) {
                initialMinOffsetError = i;
                minOffsetError = offsetError;
            }
        }

        return new OffsetError(initialMinOffsetError, minOffsetError);
    }

    private List<OffsetError> getListOfMinErrorForLanguage(List<Dictionaries> langs, int position) {
        List<OffsetError> ListOfMinimumErrorInTheLanguage = new ArrayList<OffsetError>();

//        TODO: THREAD HERE?
        for(Dictionaries iterLanguage : langs) {
            ListOfMinimumErrorInTheLanguage.add(calculateMinimumOffsetError(iterLanguage, position));
        }

        return ListOfMinimumErrorInTheLanguage;
    }


//    decript each n part of the n-key with suitable key
    private String decriptCaesar(int offset) {
        char caesarKey = this.key.charAt(offset);
        char criptoLetter;
        char plainLetter;
        String plainText = "";

        for (int i = 0; (i * this.keyLength + offset) < this.cipher.length(); i++) {
            criptoLetter = this.cipher.charAt(i * this.keyLength + offset);
            plainLetter = (char) ('A' + (26 + criptoLetter - caesarKey) % 26);
            plainText += plainLetter;
        }

        return plainText;
    }

    void decriptVigenere() {
        List<String> nPartsCipher = new ArrayList<String>();
        //TODO: THREADS HERE? I DONT THINK SO. 13*n = O(n)
//        separate in n-parts of the Kasiski. n is the key length
        for(int i = 0; i < this.keyLength; i++) {
            nPartsCipher.add(decriptCaesar(i));
        }

        int j = 0;
        for(int i = 0; i < this.cipher.length(); i++) {
            this.plainText += nPartsCipher.get(j).charAt(i/this.keyLength);
            j = (j+1) % this.keyLength;
        }
    }

}
