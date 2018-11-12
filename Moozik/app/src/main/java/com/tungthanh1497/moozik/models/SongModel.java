package com.tungthanh1497.moozik.models;

public class SongModel {
    @Override
    public String toString() {
        return "SongModel{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author + '\'' +
                ", linkSrc='" + linkSrc + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    private String title;
    private String image;
    private String author;
    private String linkSrc;
    private String id;
    private int imgId;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SongModel() {
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLinkSrc() {
        return linkSrc;
    }

    public void setLinkSrc(String linkSrc) {
        this.linkSrc = linkSrc;
    }

    public SongModel(String title, String image, String author, String linkSrc) {

        this.title = title;
        this.image = image;
        this.author = author;
        this.linkSrc = linkSrc;
    }
}
