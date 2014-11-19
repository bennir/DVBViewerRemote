package de.bennir.dvbviewerremote.modules;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }


}
