package com.tungthanh1497.moozik.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.tungthanh1497.moozik.MainActivity;
import com.tungthanh1497.moozik.R;
import com.tungthanh1497.moozik.adapters.MusicTypeAdapter;
import com.tungthanh1497.moozik.managers.ScreenManager;
import com.tungthanh1497.moozik.models.MusicTypeModel;
import com.tungthanh1497.moozik.networks.RetrofitFactory;
import com.tungthanh1497.moozik.networks.models.musicTypeJsonModels.MusicTypeResponseJsonModel;
import com.tungthanh1497.moozik.networks.models.musicTypeJsonModels.SubgenresItem;
import com.tungthanh1497.moozik.networks.services.GetMusicTypesService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicTypeFragment extends Fragment {

    FragmentActivity fragmentActivity;
    RecyclerView rvMusicTypes;
    private MusicTypeAdapter musicAdapter;
    private List<MusicTypeModel> musicTypeModelList = new ArrayList<>();
    PullRefreshLayout prlMusicType;
    Context context;

    public MusicTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getBaseContext();
        fragmentActivity = (FragmentActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music_type, container, false);
        setupUI(view);
        loadData();
        return view;
    }

    private void loadData() {
        MainActivity.nclLoading.start();
        MainActivity.nclLoading.setVisibility(View.VISIBLE);
        MainActivity.ivLoading.setVisibility(View.VISIBLE);
        final GetMusicTypesService getMusicTypesService = RetrofitFactory.getInstance().create(GetMusicTypesService.class);
        getMusicTypesService.getMusicType().enqueue(new Callback<MusicTypeResponseJsonModel>() {
            @Override
            public void onResponse(Call<MusicTypeResponseJsonModel> call, Response<MusicTypeResponseJsonModel> response) {
                List<SubgenresItem> subgenres = response.body().getSubgenres();
                for (int i = 1; i < subgenres.size(); i++) {
                    SubgenresItem musicTypesJSONModel = subgenres.get(i);
                    MusicTypeModel musicTypeModel = new MusicTypeModel();
                    musicTypeModel.setId(musicTypesJSONModel.getId());
                    musicTypeModel.setKey(musicTypesJSONModel.getTranslationKey());

//                    Log.d("14971497", "setData: "+musicTypeModel.getId());
                    musicTypeModel.setImgId(context
                            .getResources()
                            .getIdentifier("t_" + musicTypesJSONModel.getId(), "raw", context.getPackageName()));
                    musicTypeModelList.add(musicTypeModel);
//                    Log.d(TAG, "onResponse: " + subgenres.get(i));
                }
                musicAdapter.notifyDataSetChanged();
                if (MainActivity.nclLoading.isStart()) {
                    MainActivity.nclLoading.stop();
                }
                MainActivity.nclLoading.setVisibility(View.GONE);
                MainActivity.ivLoading.setVisibility(View.GONE);
//                Log.d(TAG, "\nOnResponse:\n"+response.body().getSubgenres());
            }

            @Override
            public void onFailure(Call<MusicTypeResponseJsonModel> call, Throwable t) {
                if (MainActivity.nclLoading.isStart()) {
                    MainActivity.nclLoading.stop();
                }
                MainActivity.nclLoading.setVisibility(View.GONE);
                MainActivity.ivLoading.setVisibility(View.GONE);
                Toast.makeText(context, "Some thing wrong. Try to turn on internet then reset app", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupUI(View view) {

//        prlMusicType = view.findViewById(R.id.prl_music_type);
//        prlMusicType.setRefreshStyle(PullRefreshLayout.STYLE_CIRCLES);
//        prlMusicType.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                prlMusicType.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        prlMusicType.setRefreshing(false);
//                    }
//                },3000);
////                prlMusicType.setRefreshing(true);
//            }
//        });

        rvMusicTypes = view.findViewById(R.id.rv_music_type);
        musicAdapter = new MusicTypeAdapter(musicTypeModelList, context, fragmentActivity);
        rvMusicTypes.setAdapter(musicAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position % 3 == 0 ? 2 : 1;
            }
        });

        rvMusicTypes.setLayoutManager(gridLayoutManager);
//        musicAdapter.setOnItemClick(this);
    }

//    @Override
//    public void onClick(View v) {
//        MusicTypeModel musicTypeModel = (MusicTypeModel) v.getTag();
//        EventBus.getDefault().postSticky(new OnClickMusicType(musicTypeModel));
//        ScreenManager.openFragment(getActivity().getSupportFragmentManager(), new TopSongFragment(), R.id.rl_layout_container);
//    }
}
