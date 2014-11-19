package de.bennir.dvbviewerremote.service;

import java.util.ArrayList;

import de.bennir.dvbviewerremote.model.EpgInfo;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface EpgService {
    @GET("/Epg/{id}")
    void getEpg(@Path("id") String channelId, @Query("time") String time, Callback<ArrayList<EpgInfo>> cb);
}
