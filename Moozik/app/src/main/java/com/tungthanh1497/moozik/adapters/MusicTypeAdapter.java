package com.tungthanh1497.moozik.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tungthanh1497.moozik.R;
import com.tungthanh1497.moozik.events.OnClickMusicType;
import com.tungthanh1497.moozik.fragments.TopSongFragment;
import com.tungthanh1497.moozik.managers.ScreenManager;
import com.tungthanh1497.moozik.models.MusicTypeModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class MusicTypeAdapter extends RecyclerView.Adapter<MusicTypeAdapter.MusicTypeViewHolder> {
    private List<MusicTypeModel> musicTypeModelList = new ArrayList<>();
    private Context context;
    FragmentActivity fragmentActivity;
//    private View.OnClickListener onClickListener;

//    public void setOnItemClick(View.OnClickListener onClickListener){
//        this.onClickListener = onClickListener;
//    }

    public MusicTypeAdapter(List<MusicTypeModel> musicTypeModelList, Context context, FragmentActivity fragmentActivity) {
        this.context = context;
        this.musicTypeModelList = musicTypeModelList;
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public MusicTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_music_type, parent, false);
//        view.setOnClickListener(onClickListener);
        return new MusicTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MusicTypeViewHolder holder, int position) {
        holder.setData(musicTypeModelList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MusicTypeModel musicTypeModel = musicTypeModelList.get(holder.getAdapterPosition());
                EventBus.getDefault().postSticky(new OnClickMusicType(musicTypeModel));
                ScreenManager.openFragment(fragmentActivity.getSupportFragmentManager(), new TopSongFragment(), R.id.rl_layout_container);
//                ScreenManager.replaceFragment (fragmentActivity.getSupportFragmentManager(), new TopSongFragment(), R.id.rl_layout_container);
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicTypeModelList.size();
    }

    public class MusicTypeViewHolder extends RecyclerView.ViewHolder {

        ImageView ivBackGround;
        TextView tvMusicTypes;
//        View view;

        public MusicTypeViewHolder(View itemView) {
            super(itemView);
            ivBackGround = itemView.findViewById(R.id.iv_back_ground);
            tvMusicTypes = itemView.findViewById(R.id.tv_music_types);
//            view = itemView;
        }

        public void setData(MusicTypeModel musicTypeModel) {
            if(musicTypeModel!=null){
                Log.d("14971497", "setData-img: "+musicTypeModel.getImgId());
                Picasso.with(context).load(musicTypeModel.getImgId()).into(ivBackGround);
//            ivBackGround.setImageResource(musicTypeModel.getImgId());
                tvMusicTypes.setText(musicTypeModel.getKey());
//                view.setTag(musicTypeModel);
            }
        }
    }
}
