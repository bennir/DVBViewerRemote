package de.bennir.dvbviewerremote.ui;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.bennir.dvbviewerremote.DVBRemoteApp;
import de.bennir.dvbviewerremote.R;
import timber.log.Timber;

public class ControllerActivity extends ActionBarActivity {
    @Inject
    AppContainer appContainer;

    private ViewGroup container;
    private ActionBarDrawerToggle drawerToggle;

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @InjectView(R.id.device_name) Button deviceName;
    @InjectView(R.id.list) ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DVBRemoteApp app = DVBRemoteApp.get(this);
        app.component().inject(this);

        container = appContainer.get(this);

        getLayoutInflater().inflate(R.layout.activity_controller, container);

        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.theme_default_primary_dark));

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });

        drawerLayout.setDrawerListener(drawerToggle);

        deviceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("Select Device");
            }
        });
    }

}
