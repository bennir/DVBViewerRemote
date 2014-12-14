package de.bennir.dvbviewerremote.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import javax.inject.Inject;

import de.bennir.dvbviewerremote.DVBRemoteApp;
import de.bennir.dvbviewerremote.R;

public class NsdActivity extends ActionBarActivity {
    public static final String TAG = NsdActivity.class.getName();

    @Inject AppContainer appContainer;

    private ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DVBRemoteApp app = DVBRemoteApp.get(this);
        app.component().inject(this);

        container = appContainer.get(this);

        getLayoutInflater().inflate(R.layout.activity_nsd, container);
    }
}