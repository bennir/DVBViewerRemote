package de.bennir.dvbviewerremote.ui;

import android.content.Context;
import android.content.Intent;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.bennir.dvbviewerremote.Config;
import de.bennir.dvbviewerremote.R;
import de.bennir.dvbviewerremote.model.DVBHost;
import de.bennir.dvbviewerremote.ui.adapter.NsdAdapter;

public class NsdActivity extends ActionBarActivity {
    public static final String TAG = NsdActivity.class.getName();

    @InjectView(android.R.id.list)
    ListView mListView;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;


    NsdManager mNsdManager;
    NsdManager.ResolveListener mResolveListener;
    NsdManager.DiscoveryListener mDiscoveryListener;
    ArrayList<NsdServiceInfo> mItems = new ArrayList<NsdServiceInfo>();
    NsdAdapter mAdapter;

    Handler mHandler;
    Runnable mStopDiscoveryRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                mNsdManager.stopServiceDiscovery(mDiscoveryListener);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nsd);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        setTitle("Select Device");

        mNsdManager = (NsdManager) getSystemService(NSD_SERVICE);
        initializeNsd();
        discoverServices();

        mAdapter = new NsdAdapter(getApplicationContext(), R.layout.list_item_nsd, mItems);
        mListView = (ListView) findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NsdServiceInfo nsd = mAdapter.getItem(i);
                stopDiscovery();

                if(Build.VERSION.SDK_INT >= 20) {
                    getWindow().setExitTransition(new Slide());
                }
                Intent mIntent = new Intent(getApplicationContext(), ControllerActivity.class);
                DVBHost host = new DVBHost(nsd);
                mIntent.putExtra(Config.DVBHOST_KEY, host);

                startActivity(mIntent);
            }
        });
    }

    public void initializeNsd() {
        initializeResolveListener();
        initializeDiscoveryListener();
    }

    public void initializeDiscoveryListener() {
        mDiscoveryListener = new NsdManager.DiscoveryListener() {

            @Override
            public void onDiscoveryStarted(String regType) {
                Log.d(TAG, "Service discovery started");
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                Log.d(TAG, "Service discovery success" + service);

                try {
                    mNsdManager.resolveService(service, mResolveListener);
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                Log.e(TAG, "service lost " + service);

                for (int i = 0; i < mItems.size(); i++) {
                    if (mItems.get(i).getServiceName().equalsIgnoreCase(service.getServiceName())) {
                        mItems.remove(i);

                        Log.e(TAG, "service removed " + service);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.i(TAG, "Discovery stopped: " + serviceType);
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                mNsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                mNsdManager.stopServiceDiscovery(this);
            }
        };
    }

    public void initializeResolveListener() {
        mResolveListener = new NsdManager.ResolveListener() {

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.e(TAG, "Resolve failed" + errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.e(TAG, "Resolve Succeeded. " + serviceInfo);

                mItems.add(serviceInfo);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nsd_activity, menu);
        return true;
    }

    public void discoverServices() {
        try {
            mNsdManager.discoverServices(
                    Config.SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    public void stopDiscovery() {
        mHandler = new Handler();
        mHandler.post(mStopDiscoveryRunnable);
    }
}