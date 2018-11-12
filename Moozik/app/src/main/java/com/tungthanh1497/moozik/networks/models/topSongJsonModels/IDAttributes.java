package com.tungthanh1497.moozik.networks.models.topSongJsonModels;

import com.google.gson.annotations.SerializedName;

public class IDAttributes {

    @Override
    public String toString() {
        return "IDAttributes{" +
                "imId='" + imId + '\'' +
                '}';
    }

    public String getImId() {
        return imId;
    }

    public void setImId(String imId) {
        this.imId = imId;
    }

    @SerializedName("im:id")
    private String imId;

}
