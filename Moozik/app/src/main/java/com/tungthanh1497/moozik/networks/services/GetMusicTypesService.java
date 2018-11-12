package com.tungthanh1497.moozik.networks.services;



import com.tungthanh1497.moozik.networks.models.musicTypeJsonModels.MusicTypeResponseJsonModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetMusicTypesService {
    @GET("api")
    Call<MusicTypeResponseJsonModel> getMusicType();
}
