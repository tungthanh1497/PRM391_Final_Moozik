package com.tungthanh1497.moozik.events;

import com.tungthanh1497.moozik.models.SongModel;

public class OnClickSong {
    SongModel songModel;

    public SongModel getSongModel() {
        return songModel;
    }

    public void setSongModel(SongModel songModel) {
        this.songModel = songModel;
    }

    public OnClickSong(SongModel songModel) {

        this.songModel = songModel;
    }
}
