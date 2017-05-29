package com.example.bruno.dekriptnere;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruno on 10/05/17.
 */

public class ReadFile {
    //public File LanguageFile;
    public InputStream LanguageFile;

    public ReadFile(String name, Context pContext) throws IOException {
        AssetManager pManage = pContext.getAssets();
        this.LanguageFile = pManage.open(name);
    }

    public List<Dictionaries> toList() throws IOException {
//        Creating a buffer acordding to the input files language
        BufferedReader mBufferReader = new BufferedReader(new InputStreamReader(this.LanguageFile));
        String mLine;
        List<Dictionaries> listOfFrequenciesOfSomeLanguage = new ArrayList<Dictionaries>();

        while ((mLine = mBufferReader.readLine()) != null) {
            String[] tempLine = mLine.split(";");
            List<Float> listFrequencies = new ArrayList<Float>();

            for (int i = 1; i < tempLine.length; i++) {
                listFrequencies.add(Float.parseFloat(tempLine[i]));
            }
            listOfFrequenciesOfSomeLanguage.add(new Dictionaries(tempLine[0], listFrequencies));
        }
        return listOfFrequenciesOfSomeLanguage;

    }
}
