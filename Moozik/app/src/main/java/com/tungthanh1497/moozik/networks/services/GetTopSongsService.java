package com.tungthanh1497.moozik.networks.services;

import com.tungthanh1497.moozik.networks.models.topSongJsonModels.TopSongResponseJsonModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetTopSongsService {
    @GET("https://itunes.apple.com/us/rss/topsongs/limit=100/genre={musicTypeId}/explicit=true/json")
    Call<TopSongResponseJsonModel> getTopSongs(@Path("musicTypeId") String musicTypeId);
}
