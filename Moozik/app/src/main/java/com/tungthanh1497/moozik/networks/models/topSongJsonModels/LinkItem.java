package com.tungthanh1497.moozik.networks.models.topSongJsonModels;

import com.google.gson.annotations.SerializedName;

public class LinkItem {

    @SerializedName("attributes")
    private LinkAttributes attributes;

    public void setAttributes(LinkAttributes attributes) {
        this.attributes = attributes;
    }

    public LinkAttributes getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return
                "Link{" +
                        "attributes = '" + attributes + '\'' +
                        "}";
    }
}
