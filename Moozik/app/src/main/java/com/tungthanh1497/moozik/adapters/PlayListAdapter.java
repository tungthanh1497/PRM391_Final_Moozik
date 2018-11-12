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
import com.tungthanh1497.moozik.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

//public class PlayListAdapter {
public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder> {

    FragmentActivity fragmentActivity;
    private List<SongModel> songModelList = new ArrayList<>();
    private Context context;

    public PlayListAdapter(List<SongModel> songModelList, Context context, FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
        this.songModelList = songModelList;
        this.context = context;
    }

    @Override
    public PlayListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_play_list, parent, false);
//        view.setOnClickListener(onClickListener);
        return new PlayListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PlayListViewHolder holder, final int position) {
        holder.setDatas(songModelList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongModel songModel = songModelList.get(holder.getAdapterPosition());
                EventBus.getDefault().postSticky(new OnClickSong(songModel));
                PlayMusicNotification.setupNotification(context, songModel);
                if(songModel.getImgId()!=0){
                    MusicManager.setupMusic(songModel, context);
                }else {
                    MusicManager.playListMusic(songModel, MainActivity.songModelList, context);
                }
//                ScreenManager.openFragment(fragmentActivity.getSupportFragmentManager(), new SongDetailFragment(), R.id.rl_layout_container);
                ScreenManager.openFragmentMoveUp(fragmentActivity.getSupportFragmentManager(), new SongDetailFragment(), R.id.rl_huge_container, context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songModelList.size();
    }

    public class PlayListViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPlayPause;
        TextView tvTitle;
        TextView tvAuthor;
        ImageView ivDelete;

        public PlayListViewHolder(final View itemView) {
            super(itemView);
            ivPlayPause = itemView.findViewById(R.id.iv_play_pause_play_list);
            tvTitle = itemView.findViewById(R.id.tv_title_play_list);
            tvAuthor = itemView.findViewById(R.id.tv_author_play_list);
            ivDelete = itemView.findViewById(R.id.iv_delete);

        }

        public void setDatas(final SongModel songModel) {
            if (songModel == null)
                return;
            if(songModel.getImgId()!=0){
                Picasso.with(context).load(songModel.getImgId()).transform(new CropCircleTransformation()).into(ivPlayPause);

            }else{
                Picasso.with(context).load(songModel.getImage()).transform(new CropCircleTransformation()).into(ivPlayPause);

            }
            tvTitle.setText(songModel.getTitle());
            tvAuthor.setText(songModel.getAuthor());
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<SongModel> tempList = MainActivity.songModelList;
                    for (SongModel song : tempList) {
                        if(song.getId() == songModel.getId()){
                            MainActivity.songModelList.remove(song);
                            MainActivity.savePlayListToSP(context);
                            PlayListFragment.updateDatas();
                            break;
                        }
                    }
                }
            });
        }
    }
}