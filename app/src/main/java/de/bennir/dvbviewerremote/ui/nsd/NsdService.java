package de.bennir.dvbviewerremote.ui.nsd;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Handler;
import android.util.Log;

import de.bennir.dvbviewerremote.Config;
import timber.log.Timber;

public class NsdService {
    private Context context;

    private NsdManager mNsdManager;
    private NsdManager.ResolveListener mResolveListener;
    private NsdManager.DiscoveryListener mDiscoveryListener;
    private OnNsdChangeListener onNsdChangeListener;

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

    public NsdService(Context context) {
        Timber.d("NsdService, Context=" + context.toString());
        this.context = context;

        mNsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
        Timber.d("NsdManager: " + mNsdManager.toString());

        initNsd();
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

    private void initNsd() {
        Timber.d("initNsd()");
        initResolveListener();
        initDiscoveryListener();
    }

    private void initDiscoveryListener() {
        mDiscoveryListener = new NsdManager.DiscoveryListener() {

            @Override
            public void onDiscoveryStarted(String regType) {
                Timber.d("Service discovery started");
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                Timber.d("Service discovery success" + service);

                try {
                    mNsdManager.resolveService(service, mResolveListener);
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                Timber.e("service lost " + service);

                if(onNsdChangeListener != null) {
                    onNsdChangeListener.onServiceLost(service);
                } else {
                    Timber.e("no OnNsdChangeListener set");
                }
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Timber.i("Discovery stopped: " + serviceType);
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Timber.e("Discovery failed: Error code:" + errorCode);
                mNsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Timber.e("Discovery failed: Error code:" + errorCode);
                mNsdManager.stopServiceDiscovery(this);
            }
        };
    }

    private void initResolveListener() {
        mResolveListener = new NsdManager.ResolveListener() {

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Timber.e("Resolve failed" + errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Timber.e("Resolve Succeeded. " + serviceInfo);

                if(onNsdChangeListener != null) {
                    onNsdChangeListener.onServiceResolved(serviceInfo);
                } else {
                    Timber.e("no OnNsdChangeListener set");
                }
            }
        };
    }

    public void setOnNsdChangeListener(OnNsdChangeListener onNsdChangeListener) {
        this.onNsdChangeListener = onNsdChangeListener;
    }

    public interface OnNsdChangeListener {
        void onServiceResolved(NsdServiceInfo info);
        void onServiceLost(NsdServiceInfo info);
    }
}
