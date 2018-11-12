package com.tungthanh1497.moozik.events;

import com.tungthanh1497.moozik.models.MusicTypeModel;

public class OnClickMusicType {
    MusicTypeModel musicTypeModel;

    public OnClickMusicType(MusicTypeModel musicTypeModel) {
        this.musicTypeModel = musicTypeModel;
    }

    public MusicTypeModel getMusicTypeModel() {

        return musicTypeModel;
    }

    public void setMusicTypeModel(MusicTypeModel musicTypeModel) {
        this.musicTypeModel = musicTypeModel;
    }
}
