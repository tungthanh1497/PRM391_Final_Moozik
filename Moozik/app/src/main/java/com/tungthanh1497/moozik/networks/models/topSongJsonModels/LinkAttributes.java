package com.tungthanh1497.moozik.networks.models.topSongJsonModels;

import com.google.gson.annotations.SerializedName;

public class LinkAttributes {

    @SerializedName("href")
    private String href;


    public void setHref(String href){
        this.href = href;
    }

    public String getHref(){
        return href;
    }

    @Override
    public String toString(){
        return
                "Attributes{" +
                        ",href = '" + href + '\'' +
                        "}";
    }
}