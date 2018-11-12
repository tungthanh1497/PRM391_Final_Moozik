package com.tungthanh1497.moozik.fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tungthanh1497.moozik.MainActivity;
import com.tungthanh1497.moozik.R;
import com.tungthanh1497.moozik.events.OnClickSong;
import com.tungthanh1497.moozik.managers.MusicManager;
import com.tungthanh1497.moozik.managers.ScreenManager;
import com.tungthanh1497.moozik.models.SongModel;
import com.tungthanh1497.moozik.services.PlayMusicNotification;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongDetailFragment extends Fragment {

    SongModel songModel;

    ImageView ivBackgroundDetail;
    ImageView ivBackDetail;
    ImageView ivFavorite;
    TextView tvTitleDetail;
    TextView tvAuthorDetail;
    ImageView ivAvaDetail;
    TextView tvTimeCurrent;
    TextView tvTimeEnd;
    SeekBar sbSeekbarDetail;
    ImageView ivSuffle;
    ImageView ivPrevious;
    ImageView ivNext;
    ImageView ivRepeat;
    FragmentActivity fragmentActivity;
    Context context;
    Activity activity;

    boolean isFavorited = false;


    public SongDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragmentActivity = (FragmentActivity) activity;
        context = activity.getBaseContext();
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song_detail, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(final View view) {
//        ivBackgroundCover = view.findViewById(R.id.iv_background_cover);
        ivBackgroundDetail = view.findViewById(R.id.iv_background_detail);
        ivBackDetail = view.findViewById(R.id.iv_back_detail);
        ivFavorite = view.findViewById(R.id.iv_favorite);
        tvTitleDetail = view.findViewById(R.id.tv_title_detail);
        tvAuthorDetail = view.findViewById(R.id.tv_author_detail);
        ivAvaDetail = view.findViewById(R.id.iv_ava_song_detail);
        tvTimeCurrent = view.findViewById(R.id.tv_time_current);
        tvTimeEnd = view.findViewById(R.id.tv_time_end);
        sbSeekbarDetail = view.findViewById(R.id.sb_seekbar_detail);
        ivSuffle = view.findViewById(R.id.iv_suffle);
        ivPrevious = view.findViewById(R.id.iv_previous);
        ivNext = view.findViewById(R.id.iv_next);
        ivRepeat = view.findViewById(R.id.iv_repeat);
        EventBus.getDefault().register(this);

        isFavorited = checkFavorited();
        if (songModel.getImgId() != 0) {
            Picasso.with(getContext()).load(songModel.getImgId()).transform(new BlurTransformation(getContext())).into(ivBackgroundDetail);
            Picasso.with(getContext()).load(songModel.getImgId()).transform(new CropCircleTransformation()).into(ivAvaDetail);

        } else {
            Picasso.with(getContext()).load(songModel.getImage()).transform(new BlurTransformation(getContext())).into(ivBackgroundDetail);
            Picasso.with(getContext()).load(songModel.getImage()).transform(new CropCircleTransformation()).into(ivAvaDetail);

        }
        MainActivity.setRotateAnimationSlow(ivAvaDetail);
        tvTitleDetail.setText(songModel.getTitle());
        tvAuthorDetail.setText(songModel.getAuthor());
        tvTitleDetail.setShadowLayer(30, 1, 1, Color.parseColor("#ef4f91"));
        tvAuthorDetail.setShadowLayer(30, 0, 0, Color.parseColor("#ef4f91"));
        ivBackDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
//        if (isFavorited) {
//            ivFavorite.setImageResource(R.drawable.icon_playlist_checked_detail);
////            ivFavorite.setImageResource(R.drawable.icon_heart_full);
//        } else {
//            ivFavorite.setImageResource(R.drawable.icon_playlist_add_detail);
////            ivFavorite.setImageResource(R.drawable.icon_heart_border);
//        }
        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorited) {
                    isFavorited = false;
                    ivFavorite.setImageResource(R.drawable.icon_playlist_add_detail);
//                    ivFavorite.setImageResource(R.drawable.icon_heart_border);
                    List<SongModel> tempList = MainActivity.songModelList;
                    for (SongModel song : tempList) {
                        if (song.getId() == songModel.getId()) {
                            MainActivity.songModelList.remove(song);
                            MainActivity.savePlayListToSP(getContext());
                            PlayListFragment.updateDatas();
                            break;
                        }
                    }
                } else {
                    isFavorited = true;
                    ivFavorite.setImageResource(R.drawable.icon_playlist_checked_detail);
//                    ivFavorite.setImageResource(R.drawable.icon_heart_full);
                    MainActivity.songModelList.add(songModel);
                    MainActivity.savePlayListToSP(getContext());
                    PlayListFragment.updateDatas();
                }
            }
        });
        MusicManager.updateSongRealTime(sbSeekbarDetail, tvTimeCurrent, tvTimeEnd);
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentSong = -1;
                List<SongModel> songModels = MainActivity.songModelList;
                if (songModels.size() <= 0)
                    return;
                for (int i = 0; i < songModels.size(); i++) {
                    if (songModel.getId() == songModels.get(i).getId()) {
                        currentSong = i;
                        break;
                    }
                }
                if (currentSong == songModels.size() - 1) {
                    currentSong = -1;
                }
                SongModel nextSong = songModels.get(currentSong + 1);
                EventBus.getDefault().postSticky(new OnClickSong(nextSong));
                PlayMusicNotification.setupNotification(context, nextSong);
                if (nextSong.getImgId() != 0) {
                    MusicManager.setupMusic(nextSong, context);
                } else {
                    MusicManager.playListMusic(nextSong, MainActivity.songModelList, context);
                }
                activity.onBackPressed();
                ScreenManager.openFragment(fragmentActivity.getSupportFragmentManager(), new SongDetailFragment(), R.id.rl_huge_container);
            }
        });
        ivPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SongModel> songModels = MainActivity.songModelList;
                int currentSong = songModels.size();
                if (songModels.size() <= 0)
                    return;
                for (int i = 0; i < songModels.size(); i++) {
                    if (songModel.getId() == songModels.get(i).getId()) {
                        currentSong = i;
                        break;
                    }
                }
                if (currentSong == 0) {
                    currentSong = songModels.size();
                }
                SongModel preSong = songModels.get(currentSong - 1);
                EventBus.getDefault().postSticky(new OnClickSong(preSong));
                PlayMusicNotification.setupNotification(context, preSong);
                if (preSong.getImgId() != 0) {
                    MusicManager.setupMusic(preSong, context);
                } else {
                    MusicManager.playListMusic(preSong, MainActivity.songModelList, context);
                }
                activity.onBackPressed();
                ScreenManager.openFragment(fragmentActivity.getSupportFragmentManager(), new SongDetailFragment(), R.id.rl_huge_container);
            }
        });

    }

    boolean checkFavorited() {
        List<SongModel> songList = MainActivity.songModelList;
        for (SongModel song : songList) {
            if (song.getId() == songModel.getId()) {
                return true;
            }
        }
        return false;
    }

    @Subscribe(sticky = true)
    public void onReceivedTopSong(OnClickSong onClickSong) {
        songModel = onClickSong.getSongModel();
    }

}
