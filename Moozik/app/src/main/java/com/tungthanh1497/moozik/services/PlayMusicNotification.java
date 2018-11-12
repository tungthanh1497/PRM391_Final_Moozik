package com.tungthanh1497.moozik.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.tungthanh1497.moozik.MainActivity;
import com.tungthanh1497.moozik.R;
import com.tungthanh1497.moozik.models.SongModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class PlayMusicNotification {
    public static NotificationCompat.Builder builder;
    public static RemoteViews remoteViews;
    public static NotificationManager notificationManager;

    public static void setupNotification(Context context, SongModel songModel) {

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        remoteViews.setTextViewText(R.id.tv_title_noti, songModel.getTitle());
        remoteViews.setTextViewText(R.id.tv_author_noti, songModel.getAuthor());

        Bitmap img = getBitmapFromURL(songModel.getImage());

        if (img != null) {
            remoteViews.setImageViewBitmap(R.id.iv_ava_noti, img);
        } else {
            remoteViews.setImageViewResource(R.id.iv_ava_noti, R.drawable.icon_moozik);
        }


        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.icon_moozik)
                .setContent(remoteViews)
                .setOngoing(true)
                .setContentIntent(pendingIntent);


        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
        setOnclickPlayPause(context);
    }

    public static void updateNotification(boolean isPlaying) {
        Log.d("14971497", "updateNotification: " + isPlaying);
        if (isPlaying) {
            remoteViews.setImageViewResource(R.id.iv_play_pause_noti, R.drawable.icon_pause_noti);
        } else {
            remoteViews.setImageViewResource(R.id.iv_play_pause_noti, R.drawable.icon_play_noti);
        }
        notificationManager.notify(0, builder.build());
    }

    public static void setOnclickPlayPause(Context context) {
        Intent intent = new Intent(context, PlayPauseService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_play_pause_noti, pendingIntent);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            // Log exception
            return null;
        }
    }
}
