package com.tungthanh1497.moozik.networks.models.topSongJsonModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Feed {

    @SerializedName("entry")
    private List<EntryItem> entry;


    public void setEntry(List<EntryItem> entry) {
        this.entry = entry;
    }

    public List<EntryItem> getEntry() {
        return entry;
    }


    @Override
    public String toString() {
        return
                "Feed{" +
                        "entry = '" + entry + '\'' +
                        "}";
    }
}