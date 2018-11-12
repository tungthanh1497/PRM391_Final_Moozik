package com.tungthanh1497.moozik.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tungthanh1497.moozik.MainActivity;
import com.tungthanh1497.moozik.R;
import com.tungthanh1497.moozik.adapters.TopSongAdapter;
import com.tungthanh1497.moozik.events.OnClickMusicType;
import com.tungthanh1497.moozik.models.MusicTypeModel;
import com.tungthanh1497.moozik.models.SongModel;
import com.tungthanh1497.moozik.networks.RetrofitFactory;
import com.tungthanh1497.moozik.networks.models.topSongJsonModels.EntryItem;
import com.tungthanh1497.moozik.networks.models.topSongJsonModels.ImImageItem;
import com.tungthanh1497.moozik.networks.models.topSongJsonModels.LinkItem;
import com.tungthanh1497.moozik.networks.models.topSongJsonModels.TopSongResponseJsonModel;
import com.tungthanh1497.moozik.networks.services.GetTopSongsService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopSongFragment extends Fragment {
    ImageView ivBack;
    FragmentActivity fragmentActivity;
    TextView tvNumOfSongs;
    TopSongAdapter topSongsAdapter;
    TextView tvMusicType;
    RecyclerView rvTopSong;
    //    ImageView ivBackgroundCover;
    ImageView ivMusicType;

    public TopSongFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        fragmentActivity = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_song, container, false);
        setupUI(view);
//        setupListener();
        loadDatas();
        return view;
    }

    MusicTypeModel musicTypeModel;

    private void setupUI(final View view) {
//        ivBackgroundCover = view.findViewById(R.id.iv_background_cover);
        tvNumOfSongs = view.findViewById(R.id.tv_num_of_songs);
        ivBack = view.findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        tvMusicType = view.findViewById(R.id.tv_music_type);
        rvTopSong = view.findViewById(R.id.rv_top_songs);
        ivMusicType = view.findViewById(R.id.iv_music_type);
        EventBus.getDefault().register(this);

        ivMusicType.setImageResource(musicTypeModel.getImgId());
        topSongsAdapter = new TopSongAdapter(songModelList, getContext(), fragmentActivity);
        rvTopSong.setAdapter(topSongsAdapter);

//        ivBackgroundCover.setImageResource(musicTypeModel.getImgId());
        tvMusicType.setText(musicTypeModel.getKey().toUpperCase());

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvTopSong.addItemDecoration(dividerItemDecoration);

        rvTopSong.setLayoutManager(manager);
//        topSongsAdapter.setOnItemClick(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SongModel songModel = (SongModel) v.getTag();
//
//                EventBus.getDefault().postSticky(new OnClickSong(songModel));
//                MusicManager.loadSearchSong(songModel, getContext());
//            }
//        });
//        topSongsAdapter.setOnItemClick(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RelativeLayout rlMiniPlayer = (RelativeLayout) getActivity().findViewById(R.id.rl_mini_player);
//                SeekBar sbSeekbar = (SeekBar) rlMiniPlayer.findViewById(R.id.sb_seekbar);
//                ImageView ivAvaSong = (ImageView) rlMiniPlayer.findViewById(R.id.iv_ava_song);
//                TextView tvNameSong = (TextView) rlMiniPlayer.findViewById(R.id.tv_name_song);
//                TextView tvAuthorSong = (TextView) rlMiniPlayer.findViewById(R.id.tv_author_song);
//                FloatingActionButton fabPlayButton = (FloatingActionButton) rlMiniPlayer.findViewById(R.id.fab_play_button);
//
//                SongModel songModel = (SongModel) v.getTag();
//
//
//                EventBus.getDefault().postSticky(new OnClickSong(songModel));
//                PlayMusicNotification.setupNotification(getContext(), songModel);
//
//                MusicManager.loadSearchSong(songModel, getContext(), sbSeekbar, fabPlayButton);
//
//                sbSeekbar.setPadding(0, 0, 0, 0);
////        sbSeekbar.getThumb().mutate().setAlpha(0);
//                sbSeekbar.setProgress(0);
//
//                Picasso.with(getContext()).load(songModel.getImage()).transform(new CropCircleTransformation()).into(ivAvaSong);
//                tvNameSong.setText(songModel.getTitle());
//                tvAuthorSong.setText(songModel.getAuthor());
//                rlMiniPlayer.setVisibility(View.VISIBLE);
//
//
////        ScreenManager.openFragment(getActivity().getSupportFragmentManager(), new DownloadFragment(), R.id.rl_layout_container, this);
//
//            }
//        });
    }

    List<SongModel> songModelList = new ArrayList<>();

    private void loadDatas() {
        MainActivity.nclLoading.start();
        MainActivity.nclLoading.setVisibility(View.VISIBLE);
        MainActivity.ivLoading.setVisibility(View.VISIBLE);
        final GetTopSongsService getTopSongsService = RetrofitFactory.getInstance().create(GetTopSongsService.class);
//        getTopSongs.getTopSongs(ScreenManager.musicTypeClicked.getId()).enqueue(new Callback<TopSongRespondModel>() {
        getTopSongsService.getTopSongs(musicTypeModel.getId()).enqueue(new Callback<TopSongResponseJsonModel>() {
            @Override
            public void onResponse(Call<TopSongResponseJsonModel> call, Response<TopSongResponseJsonModel> response) {
                List<EntryItem> entries = response.body().getFeed().getEntry();
                Log.d("14971497", "onResponseEntry: " + entries);
                for (EntryItem entryItem : entries) {
                    SongModel songModel = new SongModel();
                    songModel.setId(entryItem.getId().getAttributes().getImId());
                    songModel.setTitle(entryItem.getImName().getLabel());
                    songModel.setAuthor(entryItem.getImArtist().getLabel());
                    List<ImImageItem> images = entryItem.getImImage();
                    songModel.setImage(images.get(images.size() - 1).getLabel());
                    List<LinkItem> links = entryItem.getLink();
                    songModel.setLinkSrc(links.get(links.size() - 1).getAttributes().getHref());
                    songModelList.add(songModel);
//                    Log.d(TAG, "onResponse: " + subgenres.get(i));
                }
                tvNumOfSongs.setText(songModelList.size() + " songs");
                topSongsAdapter.notifyDataSetChanged();
                if (MainActivity.nclLoading.isStart()) {
                    MainActivity.nclLoading.stop();
                }
                MainActivity.nclLoading.setVisibility(View.GONE);
                MainActivity.ivLoading.setVisibility(View.GONE);
//                Log.d(TAG, "\nOnResponseList:\n" + songModelList);
            }

            @Override
            public void onFailure(Call<TopSongResponseJsonModel> call, Throwable t) {
                if (MainActivity.nclLoading.isStart()) {
                    MainActivity.nclLoading.stop();
                }
                MainActivity.nclLoading.setVisibility(View.GONE);
                MainActivity.ivLoading.setVisibility(View.GONE);
                Log.d("14971497", "onFailure: " + t.toString());
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Subscribe(sticky = true)
    public void onReceivedMusicType(OnClickMusicType onClickMusicType) {
        musicTypeModel = onClickMusicType.getMusicTypeModel();
    }


}
