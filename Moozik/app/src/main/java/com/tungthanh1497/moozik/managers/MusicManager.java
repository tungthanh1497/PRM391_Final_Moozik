package com.tungthanh1497.moozik.managers;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tungthanh1497.moozik.MainActivity;
import com.tungthanh1497.moozik.R;
import com.tungthanh1497.moozik.models.SongModel;
import com.tungthanh1497.moozik.networks.RetrofitFactory;
import com.tungthanh1497.moozik.networks.models.searchSongModels.SearchSongResponseJsonModel;
import com.tungthanh1497.moozik.networks.services.GetSearchSongService;
import com.tungthanh1497.moozik.services.PlayMusicNotification;
import com.tungthanh1497.moozik.utils.Utils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicManager {

    public static MediaPlayer mediaPlayer;
    static List<SongModel> songModels;
    static Context context;
    static SongModel isPlayingSong;
    static boolean loading = false;

    public static void playListMusic(SongModel songModelRef, final List<SongModel> songModelsRef, final Context contextRef) {
        songModels = songModelsRef;
        context = contextRef;
        loadSearchSong(songModelRef, context);

//        int currentSong = 0;
//        for (int i = 0; i < songModels.size(); i++) {
//            if (songModelRef.getId() == songModels.get(i).getId()) {
//                currentSong = i;
//                break;
//            }
//        }


//        for (int i = currentSong; i < songModels.size(); i++) {

//            if (isPlayingSong != null && isPlayingSong.getId() != songModels.get(i).getId()) {

//            if (loading) {
//                i--;
//            } else {
//                isPlayingSong = songModels.get(i);
//                if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
//                    loadSearchSong(isPlayingSong, context);
//                }
//                if (i == songModels.size()) {
//                    i = -1;
//                }
//            }
//        }
//        }
    }

    public static void loadSearchSong(final SongModel songModel, final Context context) {
        loading = true;
        MainActivity.nclLoading.start();
        MainActivity.nclLoading.setVisibility(View.VISIBLE);
        MainActivity.ivLoading.setVisibility(View.VISIBLE);
        GetSearchSongService getSearchSongService = RetrofitFactory.getInstance().create(GetSearchSongService.class);
        Log.d("14971497", "loadSearchSong: " + songModel.getTitle() + " - " + songModel.getAuthor());
        getSearchSongService.getSearchSong(songModel.getTitle() + " - " + songModel.getAuthor(), "chiasenhac.vn", "82f68e3a-320e-4c92-9ade-e088f933bf6f")
                .enqueue(new Callback<List<SearchSongResponseJsonModel>>() {
                    @Override
                    public void onResponse(Call<List<SearchSongResponseJsonModel>> call, Response<List<SearchSongResponseJsonModel>> response) {
                        if (response.body().size() > 0) {
                            List<Integer> ratioList = new ArrayList<>();
                            for (SearchSongResponseJsonModel searchSongResponseJsonModel : response.body()) {

                                int ratio = FuzzySearch.ratio(songModel.getTitle() + " " + songModel.getAuthor(),
                                        searchSongResponseJsonModel.getTitle() + " " + searchSongResponseJsonModel.getArtist());
                                ratioList.add(ratio);
                            }
                            for (int i = 0; i < ratioList.size(); i++) {
                                if (Collections.max(ratioList) == ratioList.get(i)) {
                                    String title = response.body().get(i).getTitle();
                                    String artist = response.body().get(i).getArtist();
                                    String linkResource = response.body().get(i).getUrlJunDownload();
                                    songModel.setLinkSrc(linkResource);

//                                    songModel.setLinkSrc(covertStringToURL(title + " - " + artist));
                                    setupMusic(songModel, context);
                                    Log.d("14971497", "\n" + ratioList.get(i) + "\n" + songModel.getTitle() + songModel.getAuthor() + "\n" + songModel.getLinkSrc());
                                    break;
                                }
                            }

                        } else {

                            if (MainActivity.nclLoading.isStart()) {
                                MainActivity.nclLoading.stop();
                            }
                            MainActivity.nclLoading.setVisibility(View.GONE);
                            MainActivity.ivLoading.setVisibility(View.GONE);
                            Toast.makeText(context, "Not Found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SearchSongResponseJsonModel>> call, Throwable t) {

                        if (MainActivity.nclLoading.isStart()) {
                            MainActivity.nclLoading.stop();
                        }
                        MainActivity.nclLoading.setVisibility(View.GONE);
                        MainActivity.ivLoading.setVisibility(View.GONE);
                        Log.d("14971497", "onFailure: " + t.toString());
                        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void setupMusic(final SongModel songModel, final Context context) {


//        String url = "https://www.sample-videos.com/audio/mp3/crowd-cheering.mp3"; // your URL here
//        String url = "https://api.soundcloud.com/tracks/301689536/download"; // your URL here
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        String url = songModel.getLinkSrc(); // your URL here
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    if (MainActivity.nclLoading.isStart()) {
                        MainActivity.nclLoading.stop();
                    }
                    MainActivity.nclLoading.setVisibility(View.GONE);
                    MainActivity.ivLoading.setVisibility(View.GONE);
                    MainActivity.updateMiniPlayer(songModel, context);
                    mp.start();
                    loading = false;
                    updateSongRealTime(MainActivity.sbMini, null, null);
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (MainActivity.nclLoading.isStart()) {
                        MainActivity.nclLoading.stop();
                    }
                    MainActivity.nclLoading.setVisibility(View.GONE);
                    MainActivity.ivLoading.setVisibility(View.GONE);
                    // dissmiss progress bar here. It will come here when MediaPlayer
                    //  is not able to play file. You can show error message to user
                    return false;
                }
            });
        } catch (Exception e) {
            if (MainActivity.nclLoading.isStart()) {
                MainActivity.nclLoading.stop();
            }
            MainActivity.nclLoading.setVisibility(View.GONE);
            MainActivity.ivLoading.setVisibility(View.GONE);
            e.printStackTrace();
        }

    }

    public static void playPause() {
        if (mediaPlayer == null) {
            return;
        }
        Log.d("14971497", "playPause: " + mediaPlayer.isPlaying());
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            currentPosition[0] = mediaPlayer.getCurrentPosition();
        } else {
            mediaPlayer.seekTo(currentPosition[0]);
            mediaPlayer.start();
        }
        PlayMusicNotification.updateNotification(mediaPlayer.isPlaying());
    }

    public static int[] currentPosition = new int[1];

    public static void updateSongRealTime(final SeekBar sb, final TextView tvCurrent, final TextView tvEnd) {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    sb.setMax(mediaPlayer.getDuration());
                    sb.setProgress(mediaPlayer.getCurrentPosition());
                    if (mediaPlayer.isPlaying()) {
                        MainActivity.fabPlayPause.setImageResource(R.drawable.icon_pause);
                    } else {
                        MainActivity.fabPlayPause.setImageResource(R.drawable.icon_play);
                    }
                    if (tvCurrent != null && tvEnd != null) {
                        tvEnd.setText(Utils.convertTime(mediaPlayer.getDuration()));
                        tvCurrent.setText(Utils.convertTime(mediaPlayer.getCurrentPosition()));
                    }
                }
                handler.postDelayed(this, 100);

            }
        };
        runnable.run();

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentPosition[0] = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(currentPosition[0]);
            }
        });
    }

    public static String covertStringToURL(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String result = "http://data31.chiasenhac.com/downloads/1969/4/1968062-d6715440/128/"
                    + pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "%20")
                    .replaceAll("[\\.,;()]", "_")
                    .replaceAll("đ", "d")
                    + "%20[128kbps_MP3].mp3";
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
