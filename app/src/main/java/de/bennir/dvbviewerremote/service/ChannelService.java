package de.bennir.dvbviewerremote.service;

import java.util.ArrayList;

import de.bennir.dvbviewerremote.model.Channel;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface ChannelService {
    @GET("/Channel/current")
    void getCurrentChannel(Callback<Channel> cb);

    @GET("/Channel")
    void getChannels(Callback<ArrayList<Channel>> cb);

    @GET("/Channel/{id}")
    void getChannel(@Path("id") int id, Callback<Channel> cb);

    @GET("/Channel/current/name")
    void getCurrentChannelName(Callback<String> cb);

    @POST("/Channel")
    void setChannel(@Body Channel channelId, Callback<Channel> cb);
}
