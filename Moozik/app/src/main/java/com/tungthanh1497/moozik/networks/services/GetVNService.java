package com.tungthanh1497.moozik.networks.services;

import com.tungthanh1497.moozik.networks.models.vnJsonModels.VNResponseJsonModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface GetVNService {
    @GET("https://rss.itunes.apple.com/api/v1/vn/apple-music/{listType}/all/100/explicit.json")
    Call<VNResponseJsonModel> getTopSongs(@Path("listType") String listType);
}
