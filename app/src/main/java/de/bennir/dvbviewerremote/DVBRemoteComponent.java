package de.bennir.dvbviewerremote;

import javax.inject.Singleton;

import dagger.Component;
import de.bennir.dvbviewerremote.ui.ControllerActivity;
import de.bennir.dvbviewerremote.ui.MainUiModule;
import de.bennir.dvbviewerremote.ui.NsdActivity;

@Singleton
@Component(modules = { DVBRemoteAppModule.class, MainUiModule.class})
public interface DVBRemoteComponent {
    final static class Initializer {
        static DVBRemoteComponent init(DVBRemoteApp app) {
            return Dagger_DVBRemoteComponent.builder()
                    .dVBRemoteAppModule(new DVBRemoteAppModule(app))
                    .build();
        }
        private Initializer() {} // No instances.
    }

    void inject(DVBRemoteApp app);
    void inject(NsdActivity activity);
    void inject(ControllerActivity activity);
}
