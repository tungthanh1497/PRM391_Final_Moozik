package com.tungthanh1497.moozik.models;

import java.util.ArrayList;

public class ListTypeVNModel {
    private String headerTitle;
    private ArrayList<SongModel> listSongs;

    public ListTypeVNModel(String headerTitle, ArrayList<SongModel> listSongs) {
        this.headerTitle = headerTitle;
        this.listSongs = listSongs;
    }

    @Override
    public String toString() {
        return "ListTypeVNModel{" +
                "headerTitle='" + headerTitle + '\'' +
                ", listSongs=" + listSongs +
                '}';
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<SongModel> getListSongs() {
        return listSongs;
    }

    public void setListSongs(ArrayList<SongModel> listSongs) {
        this.listSongs = listSongs;
    }
}
