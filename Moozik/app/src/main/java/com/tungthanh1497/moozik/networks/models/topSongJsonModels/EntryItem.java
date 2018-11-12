package com.tungthanh1497.moozik.networks.models.topSongJsonModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EntryItem {

    @SerializedName("im:artist")
    private ImArtist imArtist;

    @SerializedName("im:name")
    private ImName imName;

    @SerializedName("id")
    private Id id;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    @SerializedName("im:image")
    private List<ImImageItem> imImage;

    @SerializedName("link")
    private List<LinkItem> link;

    public List<LinkItem> getLink() {
        return link;
    }

    public void setLink(List<LinkItem> link) {
        this.link = link;
    }

    public void setImArtist(ImArtist imArtist) {
        this.imArtist = imArtist;
    }

    public ImArtist getImArtist() {
        return imArtist;
    }

    public void setImName(ImName imName) {
        this.imName = imName;
    }

    public ImName getImName() {
        return imName;
    }


    public void setImImage(List<ImImageItem> imImage) {
        this.imImage = imImage;
    }

    public List<ImImageItem> getImImage() {
        return imImage;
    }

    @Override
    public String toString() {
        return "EntryItem{" +
                "imArtist=" + imArtist +
                ", imName=" + imName +
                ", imImage=" + imImage +
                ", link=" + link +
                '}';
    }
}