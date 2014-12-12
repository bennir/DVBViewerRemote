package de.bennir.dvbviewerremote.ui.nsd;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import de.bennir.dvbviewerremote.modules.NsdModule;

@Singleton
@Component(modules = {NsdModule.class})
public interface NsdComponent {
    final static class Initializer {
        static NsdComponent init(Context context) {
            return Dagger_NsdComponent.builder()
                    .nsdModule(new NsdModule(context))
                    .build();
        }

        private Initializer() {
        } // No instances.
    }

    NsdService service();

    void inject(NsdView view);
}
