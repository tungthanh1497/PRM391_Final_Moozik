package com.tungthanh1497.moozik.models;

public class MusicTypeModel {
    public MusicTypeModel() {
    }

    public MusicTypeModel(String id, String key, int imgId) {
        this.id = id;
        this.key = key;
        this.imgId = imgId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    private String id;
    private String key;
    private int imgId;
}
