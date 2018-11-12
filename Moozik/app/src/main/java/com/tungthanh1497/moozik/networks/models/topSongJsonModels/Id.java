package com.tungthanh1497.moozik.networks.models.topSongJsonModels;

import com.google.gson.annotations.SerializedName;

public class Id {

    @SerializedName("attributes")
    private IDAttributes attributes;

    public void setAttributes(IDAttributes attributes) {
        this.attributes = attributes;
    }

    public IDAttributes getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return
                "Id{" +
                        ",attributes = '" + attributes + '\'' +
                        "}";
    }
}