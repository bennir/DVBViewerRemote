package de.bennir.dvbviewerremote.service;

import de.bennir.dvbviewerremote.model.DVBCommand;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface CommandService {
    @POST("/Command")
    void sendCommand(@Body DVBCommand command, Callback<DVBCommand> cb);
}
