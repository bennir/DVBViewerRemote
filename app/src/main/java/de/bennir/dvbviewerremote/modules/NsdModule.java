package de.bennir.dvbviewerremote.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.bennir.dvbviewerremote.ui.nsd.NsdService;

@Module
public class NsdModule {
    private final Context context;

    public NsdModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton NsdService proviceNsdService() {
        return new NsdService(context);
    }
}
