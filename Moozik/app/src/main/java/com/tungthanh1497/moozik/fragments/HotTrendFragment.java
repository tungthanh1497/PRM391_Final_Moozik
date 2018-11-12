package com.tungthanh1497.moozik.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tungthanh1497.moozik.R;
import com.tungthanh1497.moozik.adapters.HotTrendAdapter;

import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotTrendFragment extends Fragment {

    RecyclerView rvHotTrend;
    HotTrendAdapter hotTrendAdapter;

    public HotTrendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_hot_trend, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(View view) {
//        rvHotTrend = view.findViewById(R.id.rv_hot_trend);
//
//        hotTrendAdapter = new HotTrendAdapter(topSongModelList, getContext(), fragmentActivity);
//        rvTopSong.setAdapter(topSongsAdapter);
//
////        ivBackgroundCover.setImageResource(musicTypeModel.getImgId());
//        tvMusicType.setText(musicTypeModel.getKey().toUpperCase());
//
//        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
//        rvTopSong.addItemDecoration(dividerItemDecoration);
//
//        rvTopSong.setLayoutManager(manager);
    }

}
