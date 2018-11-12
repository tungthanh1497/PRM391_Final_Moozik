package com.tungthanh1497.moozik.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tungthanh1497.moozik.MainActivity;
import com.tungthanh1497.moozik.R;
import com.tungthanh1497.moozik.adapters.PlayListAdapter;
import com.tungthanh1497.moozik.models.SongModel;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayListFragment extends Fragment {

    RecyclerView rvPlayList;
    static PlayListAdapter playListAdapter;
    static List<SongModel> songModelList = new ArrayList<>();
    FragmentActivity fragmentActivity;
    Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragmentActivity = (FragmentActivity) activity;
        context=activity.getBaseContext();
    }

    public PlayListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play_list, container, false);
        setupUI(view);
        updateDatas();
        return view;
    }

    public static void updateDatas() {
        songModelList = MainActivity.songModelList;
        Log.d("14971497", "loadDatas: "+songModelList);
        playListAdapter.notifyDataSetChanged();
    }

    private void setupUI(View view) {

        rvPlayList = view.findViewById(R.id.rv_play_list);
        songModelList = MainActivity.songModelList;
        playListAdapter = new PlayListAdapter(songModelList, context, fragmentActivity);
        rvPlayList.setAdapter(playListAdapter);


        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        rvPlayList.addItemDecoration(dividerItemDecoration);

        rvPlayList.setLayoutManager(manager);
    }


}
