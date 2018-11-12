package com.tungthanh1497.moozik.networks.services;

import com.tungthanh1497.moozik.networks.models.searchSongModels.SearchSongResponseJsonModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetSearchSongService {
    @GET("http://j.ginggong.com/jOut.ashx")
    Call<List<SearchSongResponseJsonModel>> getSearchSong(@Query("k") String nameSong, @Query("h") String site, @Query("code") String accessCode);
}
