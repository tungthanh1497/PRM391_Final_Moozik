package com.tungthanh1497.moozik.networks.models.vnJsonModels;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Feed {
    @SerializedName("results")
    private List<ResultsItem> results;


    public void setResults(List<ResultsItem> results) {
        this.results = results;
    }

    public List<ResultsItem> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return
                "Feed{" +
                        ",results = '" + results + '\'' +
                        "}";
    }
}