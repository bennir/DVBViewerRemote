package de.bennir.dvbviewerremote.modules;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.bennir.dvbviewerremote.service.ChannelService;
import de.bennir.dvbviewerremote.service.CommandService;
import de.bennir.dvbviewerremote.service.EpgService;
import retrofit.Endpoint;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

@Module(
        complete = false,
        library = true
)
public class ApiModule {
    private final Endpoint endpoint;

    public ApiModule(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Provides @Singleton RestAdapter provideRestAdapter(Endpoint endpoint, OkHttpClient client) {
        return new RestAdapter.Builder()
                .setClient(new OkClient(client))
                .setEndpoint(endpoint)
                .build();
    }

    @Provides @Singleton ChannelService provideChannelService(RestAdapter restAdapter) {
        return restAdapter.create(ChannelService.class);
    }

    @Provides @Singleton CommandService provideCommandService(RestAdapter restAdapter) {
        return restAdapter.create(CommandService.class);
    }

    @Provides @Singleton EpgService provideEpgService(RestAdapter restAdapter) {
        return restAdapter.create(EpgService.class);
    }
}
