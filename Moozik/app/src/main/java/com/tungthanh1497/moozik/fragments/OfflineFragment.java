package com.tungthanh1497.moozik.fragments;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tungthanh1497.moozik.R;
import com.tungthanh1497.moozik.adapters.OfflineAdapter;
import com.tungthanh1497.moozik.models.SongModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfflineFragment extends Fragment {
    List<SongModel> songs;
    RecyclerView recyclerViewListSong;
    TextView txtCurrentSongName, txtCurrentSingerName;
    RelativeLayout currentSongLayout;
    Activity activity;
    OfflineAdapter offlineAdapter;
    Context context;
    FragmentActivity fragmentActivity;

    public OfflineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;
        this.context = activity.getBaseContext();
        this.fragmentActivity = (FragmentActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offline, container, false);

        setupUI(view);
        loadDatas();


        return view;
    }

    private void loadDatas() {
        getMusic();
        offlineAdapter = new OfflineAdapter(songs, context, fragmentActivity);
        recyclerViewListSong.setAdapter(offlineAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerViewListSong.addItemDecoration(dividerItemDecoration);
        recyclerViewListSong.setLayoutManager(manager);
    }

    private void setupUI(View view) {
        recyclerViewListSong = view.findViewById(R.id.rv_offline);
        songs=new ArrayList<>();
    }


    public void getMusic(){

        ContentResolver contentResolver = activity.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if(songCursor != null && songCursor.moveToFirst()){
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);

            do{
                String name = songCursor.getString(songTitle);
                String singer = songCursor.getString(songArtist);
                String path = songCursor.getString(songData);
                String id = songCursor.getString(songId);
                SongModel songModel = new SongModel(name, singer, singer, path);
                songModel.setImgId(R.drawable.icon_moozik);
                songModel.setId(id);
                Log.d("14971497", "getMusic: "+songs);
                songs.add(songModel);
            } while(songCursor.moveToNext());
        }
    }

}
