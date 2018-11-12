package com.tungthanh1497.moozik.fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.tungthanh1497.moozik.MainActivity;
import com.tungthanh1497.moozik.R;
import com.tungthanh1497.moozik.adapters.SearchSongAdapter;
import com.tungthanh1497.moozik.models.SongModel;
import com.tungthanh1497.moozik.networks.RetrofitFactory;
import com.tungthanh1497.moozik.networks.models.searchSongModels.SearchSongResponseJsonModel;
import com.tungthanh1497.moozik.networks.services.GetSearchSongService;
import com.victor.loading.newton.NewtonCradleLoading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }

    SearchView searchView;
    List<SongModel> songModelList;
    RecyclerView rvSearchSong;
    SearchSongAdapter searchSongAdapter;

    FragmentActivity fragmentActivity;
    Context context;


    @Override
    public void onAttach(Activity activity) {
        fragmentActivity = (FragmentActivity) activity;
        context = activity.getBaseContext();
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        setupUI(view);

        RxSearchView.queryTextChanges(searchView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {
                        return charSequence.length() > 1;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        Log.d("14971497", "accept: "+charSequence);
                        MainActivity.nclLoading.start();
                        MainActivity.nclLoading.setVisibility(View.VISIBLE);
                        MainActivity.ivLoading.setVisibility(View.VISIBLE);
                        GetSearchSongService getSearchSongService = RetrofitFactory.getInstance().create(GetSearchSongService.class);
                        getSearchSongService.getSearchSong(charSequence + "", "chiasenhac.vn", "82f68e3a-320e-4c92-9ade-e088f933bf6f")
                                .enqueue(new Callback<List<SearchSongResponseJsonModel>>() {
                                    @Override
                                    public void onResponse(Call<List<SearchSongResponseJsonModel>> call, Response<List<SearchSongResponseJsonModel>> response) {
                                        if (response.body().size() > 0) {
                                            songModelList = new ArrayList<>();
                                            Log.d("14971497", "onResponse: "+response.body().size());
                                            for (SearchSongResponseJsonModel searchSongResponseJsonModel : response.body()) {
                                                SongModel songModel = new SongModel();
                                                songModel.setId(searchSongResponseJsonModel.getId());
                                                songModel.setTitle(searchSongResponseJsonModel.getTitle());
                                                songModel.setAuthor(searchSongResponseJsonModel.getArtist());
                                                songModel.setImage(searchSongResponseJsonModel.getAvatar());
                                                songModel.setLinkSrc(searchSongResponseJsonModel.getUrlJunDownload());
                                                Log.d("14971497", "onResponse: "+songModel);
                                                songModelList.add(songModel);
                                            }
                                            Log.d("14971497", "onResponse: "+songModelList.size());
                                            searchSongAdapter = new SearchSongAdapter(songModelList, context, fragmentActivity);
                                            rvSearchSong.setAdapter(searchSongAdapter);
//                                            searchSongAdapter.notifyDataSetChanged();
                                            Log.d("14971497", "onResponse: "+searchSongAdapter.getItemCount());
                                            if (MainActivity.nclLoading.isStart()) {
                                                MainActivity.nclLoading.stop();
                                            }
                                            MainActivity.nclLoading.setVisibility(View.GONE);
                                            MainActivity.ivLoading.setVisibility(View.GONE);

                                        } else {

                                            if (MainActivity.nclLoading.isStart()) {
                                                MainActivity.nclLoading.stop();
                                            }
                                            MainActivity.nclLoading.setVisibility(View.GONE);
                                            MainActivity.ivLoading.setVisibility(View.GONE);
                                            Toast.makeText(context, "Not Found", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<SearchSongResponseJsonModel>> call, Throwable t) {

                                        if (MainActivity.nclLoading.isStart()) {
                                            MainActivity.nclLoading.stop();
                                        }
                                        MainActivity.nclLoading.setVisibility(View.GONE);
                                        MainActivity.ivLoading.setVisibility(View.GONE);
                                        Log.d("14971497", "onFailure: " + t.toString());
                                        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });
        return view;
    }

    private void setupUI(View view) {
        searchView = view.findViewById(R.id.sv_search_song);
        songModelList = new ArrayList<>();
//        songModelList.add(new SongModel( "title", R.drawable.icon_moozik+"", "author", "linkSrc"));
        rvSearchSong = view.findViewById(R.id.rv_search_song);
        searchSongAdapter = new SearchSongAdapter(songModelList, context, fragmentActivity);
        rvSearchSong.setAdapter(searchSongAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        rvSearchSong.addItemDecoration(dividerItemDecoration);
        rvSearchSong.setLayoutManager(manager);

    }

}
