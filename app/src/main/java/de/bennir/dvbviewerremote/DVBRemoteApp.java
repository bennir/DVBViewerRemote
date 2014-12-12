package de.bennir.dvbviewerremote;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import de.bennir.dvbviewerremote.ui.ActivityHierarchyServer;
import hugo.weaving.DebugLog;
import timber.log.Timber;

public class DVBRemoteApp extends Application {
    private DVBRemoteComponent component;

    @Inject
    ActivityHierarchyServer activityHierarchyServer;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            // TODO Crashlytics.start(this);
            // TODO Timber.plant(new CrashlyticsTree());
        }

        buildComponentAndInject();

        registerActivityLifecycleCallbacks(activityHierarchyServer);
    }

    @DebugLog
    public void buildComponentAndInject() {
        component = DVBRemoteComponent.Initializer.init(this);
        component.inject(this);
    }

    public DVBRemoteComponent component() {
        return component;
    }

    public static DVBRemoteApp get(Context context) {
        return (DVBRemoteApp) context.getApplicationContext();
    }

}