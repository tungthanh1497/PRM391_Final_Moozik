package com.tungthanh1497.moozik.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class OfflineAdapter extends RecyclerView.Adapter<OfflineAdapter.OfflineViewHolder> {

    FragmentActivity fragmentActivity;
    private List<SongModel> songModelList = new ArrayList<>();
    private Context context;
//    private View view;
//    private View.OnClickListener onClickListener;


//    public void setOnItemClick(View.OnClickListener onClickListener) {
//        this.onClickListener = onClickListener;
//    }

    public OfflineAdapter(List<SongModel> songModelList, Context context, FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
        this.songModelList = songModelList;
        this.context = context;
    }

    @Override
    public OfflineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_top_song, parent, false);
//        view.setOnClickListener(onClickListener);
        return new OfflineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OfflineViewHolder holder, final int position) {
        holder.setDatas(songModelList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongModel songModel = songModelList.get(holder.getAdapterPosition());
                EventBus.getDefault().postSticky(new OnClickSong(songModel));
                PlayMusicNotification.setupNotification(context, songModel);
                MusicManager.setupMusic(songModel, context);
//                MusicManager.playListMusic(songModel, MainActivity.songModelList, context);

//                ScreenManager.openFragment(fragmentActivity.getSupportFragmentManager(), new SongDetailFragment(), R.id.rl_layout_container);
                ScreenManager.openFragmentMoveUp(fragmentActivity.getSupportFragmentManager(), new SongDetailFragment(), R.id.rl_huge_container, context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songModelList.size();
    }

    public class OfflineViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_ava_song;
        TextView tv_name_song;
        TextView tv_author_song;
        ImageView ivAddToPlayList;
        boolean isFavorited = false;

        public OfflineViewHolder(final View itemView) {
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

//            if (isFavorited) {
//                Picasso.with(context).load(R.drawable.icon_playlist_checked).into(ivAddToPlayList);
//            } else {
//                Picasso.with(context).load(R.drawable.icon_playlist_add).into(ivAddToPlayList);
//            }
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
            Picasso.with(context).load(songModel.getImgId()).transform(new CropCircleTransformation()).into(iv_ava_song);
            Log.d("14971497", "setDatas: " + isFavorited);
            tv_name_song.setText(songModel.getTitle());
            tv_author_song.setText(songModel.getAuthor());
//            view.setTag(songModel);
        }
    }
}