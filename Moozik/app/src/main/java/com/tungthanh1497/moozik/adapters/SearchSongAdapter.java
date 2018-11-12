package com.tungthanh1497.moozik.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tungthanh1497.moozik.MainActivity;
import com.tungthanh1497.moozik.R;
import com.tungthanh1497.moozik.events.OnClickSong;
import com.tungthanh1497.moozik.fragments.PlayListFragment;
import com.tungthanh1497.moozik.fragments.SongDetailFragment;
import com.tungthanh1497.moozik.managers.MusicManager;
import com.tungthanh1497.moozik.managers.ScreenManager;
import com.tungthanh1497.moozik.models.SongModel;
import com.tungthanh1497.moozik.services.PlayMusicNotification;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class SearchSongAdapter extends RecyclerView.Adapter<SearchSongAdapter.SearchSongViewHolder> {

    FragmentActivity fragmentActivity;
    private List<SongModel> songModelList = new ArrayList<>();
    private Context context;
//    private View view;
//    private View.OnClickListener onClickListener;


//    public void setOnItemClick(View.OnClickListener onClickListener) {
//        this.onClickListener = onClickListener;
//    }

    public SearchSongAdapter(List<SongModel> songModelList, Context context, FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
        this.songModelList = songModelList;
        this.context = context;
    }

    @Override
    public SearchSongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_top_song, parent, false);
//        view.setOnClickListener(onClickListener);
        return new SearchSongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchSongViewHolder holder, final int position) {
        holder.setDatas(songModelList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongModel songModel = songModelList.get(holder.getAdapterPosition());
                EventBus.getDefault().postSticky(new OnClickSong(songModel));
                PlayMusicNotification.setupNotification(context, songModel);
                MusicManager.playListMusic(songModel, MainActivity.songModelList, context);

//                ScreenManager.openFragment(fragmentActivity.getSupportFragmentManager(), new SongDetailFragment(), R.id.rl_layout_container);
                ScreenManager.openFragmentMoveUp(fragmentActivity.getSupportFragmentManager(), new SongDetailFragment(), R.id.rl_huge_container, context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songModelList.size();
    }

    public class SearchSongViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_ava_song;
        TextView tv_name_song;
        TextView tv_author_song;
        ImageView ivAddToPlayList;
        boolean isFavorited = false;

        public SearchSongViewHolder(final View itemView) {
            super(itemView);
            iv_ava_song = (ImageView) itemView.findViewById(R.id.iv_ava_song);
            tv_name_song = (TextView) itemView.findViewById(R.id.tv_name_song);
            tv_author_song = (TextView) itemView.findViewById(R.id.tv_author_song);
            ivAddToPlayList = itemView.findViewById(R.id.iv_add_playlist);

        }


        boolean checkFavorited(SongModel songModel) {
            List<SongModel> songList = MainActivity.songModelList;
            for (SongModel song : songList) {
                if (song.getId() == songModel.getId()) {
                    return true;
                }
            }
            return false;
        }

        public void setDatas(final SongModel songModel) {
            if (songModel == null)
                return;
            isFavorited = checkFavorited(songModel);
            ivAddToPlayList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isFavorited) {
                        isFavorited = false;
                        ivAddToPlayList.setImageResource(R.drawable.icon_playlist_add);
//                        ivAddToPlayList.setImageResource(R.drawable.icon_heart_tab_border);
                        List<SongModel> tempList = MainActivity.songModelList;
                        for (SongModel song : tempList) {
                            if (song.getId() == songModel.getId()) {
                                MainActivity.songModelList.remove(song);
                                MainActivity.savePlayListToSP(context);
                                PlayListFragment.updateDatas();
                                break;
                            }
                        }
                    } else {
                        isFavorited = true;
                        ivAddToPlayList.setImageResource(R.drawable.icon_playlist_checked);
//                        ivAddToPlayList.setImageResource(R.drawable.icon_heart_full);
                        MainActivity.songModelList.add(songModel);
                        MainActivity.savePlayListToSP(context);
                        PlayListFragment.updateDatas();
                    }
                }
            });
//            Picasso.with(context).load(songModel.getImage()).into(iv_ava_song);

//            if (isFavorited) {
//                Picasso.with(context).load(R.drawable.icon_playlist_checked).into(ivAddToPlayList);
//            } else {
//
//                Picasso.with(context).load(R.drawable.icon_playlist_add).into(ivAddToPlayList);
//            }
//
//            if (isFavorited) {
//                Picasso.with(context).load("https://cdn0.iconfinder.com/data/icons/small-n-flat/24/678134-sign-check-128.png").into(ivAddToPlayList);
//            } else {
//                Picasso.with(context).load("https://cdn3.iconfinder.com/data/icons/sympletts-free-sampler/128/circle-check-128.png").into(ivAddToPlayList);
//            }
            Picasso.with(context).load(songModel.getImage()).transform(new CropCircleTransformation()).into(iv_ava_song);
            tv_name_song.setText(songModel.getTitle());
            tv_author_song.setText(songModel.getAuthor());
//            view.setTag(songModel);
        }
    }
}