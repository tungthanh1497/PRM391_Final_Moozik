package com.tungthanh1497.moozik.networks.models.searchSongModels;

import com.google.gson.annotations.SerializedName;

public class SearchSongResponseJsonModel {

    @SerializedName("Artist")
    private String artist;


    @SerializedName("Title")
    private String title;

    @SerializedName("Id")
    private String id;

    @SerializedName("UrlJunDownload")
    private String urlJunDownload;

    @SerializedName("Avatar")
    private String avatar;

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUrlJunDownload(String urlJunDownload) {
        this.urlJunDownload = urlJunDownload;
    }

    public String getUrlJunDownload() {
        return urlJunDownload;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }


    @Override
    public String toString() {
        return
                "SearchSongResponseJsonModel{" +
                        "artist = '" + artist + '\'' +
                        ",title = '" + title + '\'' +
                        ",id = '" + id + '\'' +
                        ",urlJunDownload = '" + urlJunDownload + '\'' +
                        ",avatar = '" + avatar + '\'' +
                        "}";
    }
}