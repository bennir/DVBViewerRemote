package de.bennir.dvbviewerremote;

import de.bennir.dvbviewerremote.ui.NsdActivity;
import de.bennir.dvbviewerremote.ui.nsd.NsdView;

public interface DVBRemoteGraph {
    void inject(DVBRemoteApp app);
    void inject(NsdActivity activity);
    void inject(NsdView view);
}
