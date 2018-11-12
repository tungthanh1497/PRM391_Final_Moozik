package com.tungthanh1497.moozik.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.tungthanh1497.moozik.managers.MusicManager;

public class PlayPauseService extends Service {
    public PlayPauseService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MusicManager.playPause();
        return super.onStartCommand(intent, flags, startId);
    }
}
