package com.tungthanh1497.moozik.networks.models.vnJsonModels;

import com.google.gson.annotations.SerializedName;

public class ResultsItem {

    @SerializedName("artworkUrl100")
    private String artworkUrl100;

    @SerializedName("name")
    private String name;

    @SerializedName("artistName")
    private String artistName;

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistName() {
        return artistName;
    }

    @Override
    public String toString() {
        return
                "ResultsItem{" +
                        "artworkUrl100 = '" + artworkUrl100 + '\'' +
                        ",name = '" + name + '\'' +
                        ",artistName = '" + artistName + '\'' +
                        "}";
    }
}