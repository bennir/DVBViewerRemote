package de.bennir.dvbviewerremote.ui;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainUiModule {
    @Provides @Singleton AppContainer provideAppContainer() {
        return AppContainer.DEFAULT;
    }

    @Provides @Singleton ActivityHierarchyServer provideActivityHierarchyServer() {
        return ActivityHierarchyServer.NONE;
    }
}