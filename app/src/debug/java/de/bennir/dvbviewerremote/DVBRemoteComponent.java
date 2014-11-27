package de.bennir.dvbviewerremote;

import javax.inject.Singleton;

import dagger.Component;
import de.bennir.dvbviewerremote.ui.MainUiModule;

@Singleton
@Component(modules = { DVBRemoteAppModule.class, MainUiModule.class})
public interface DVBRemoteComponent extends DVBRemoteGraph {
    final static class Initializer {
        static DVBRemoteGraph init(DVBRemoteApp app) {
            return Dagger_DVBRemoteComponent.builder()
                    .dVBRemoteAppModule(new DVBRemoteAppModule(app))
                    .build();
        }
        private Initializer() {} // No instances.
    }
}
