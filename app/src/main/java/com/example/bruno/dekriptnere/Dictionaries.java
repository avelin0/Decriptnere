package com.example.bruno.dekriptnere;

import java.util.List;
/**
 * Created by bruno on 10/05/17.
 */

public class Dictionaries {
    private String language;
    private List<Float> frequencies;

    public Dictionaries(String language, List<Float> frequencies) {
        this.language = language;
        this.frequencies = frequencies;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Float> getFrequencies() {
        return frequencies;
    }

    public void setFrequencies(List<Float> frequencies) {
        this.frequencies = frequencies;
    }
}
