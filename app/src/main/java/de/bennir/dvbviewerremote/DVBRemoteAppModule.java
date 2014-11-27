package de.bennir.dvbviewerremote;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DVBRemoteAppModule {
    private final DVBRemoteApp app;

    public DVBRemoteAppModule(DVBRemoteApp app) {
        this.app = app;
    }

    @Provides @Singleton
    Application provideApplication() {
        return app;
    }
}
