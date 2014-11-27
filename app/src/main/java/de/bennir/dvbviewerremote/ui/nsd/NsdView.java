package de.bennir.dvbviewerremote.ui.nsd;

import android.content.Context;
import android.net.nsd.NsdServiceInfo;
import android.util.AttributeSet;

import java.util.List;

import de.bennir.dvbviewerremote.DVBRemoteApp;
import de.bennir.dvbviewerremote.R;
import de.bennir.dvbviewerremote.ui.misc.BetterViewAnimator;

public class NsdView extends BetterViewAnimator {
    private final NsdAdapter adapter;
    private List<NsdServiceInfo> items;

    public NsdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        DVBRemoteApp.get(context).component().inject(this);

        adapter = new NsdAdapter(context, R.layout.list_item_nsd, items);
    }
}
