package de.bennir.dvbviewerremote.ui.nsd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.nsd.NsdServiceInfo;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.bennir.dvbviewerremote.Config;
import de.bennir.dvbviewerremote.R;
import de.bennir.dvbviewerremote.model.DVBHost;
import de.bennir.dvbviewerremote.ui.ControllerActivity;
import timber.log.Timber;

public class NsdView extends LinearLayout {
    @InjectView(android.R.id.list) ListView listView;
    @InjectView(R.id.toolbar) Toolbar toolbar;

    private Context context;

    private NsdAdapter mAdapter;
    private List<NsdServiceInfo> mItems = new ArrayList<>();
    private View headerView;
    private ImageView refresh;

    @Inject NsdService nsdService;

    public NsdView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        NsdComponent.Initializer.init(context).inject(this);
    }

    private void updateListView() {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mAdapter.getCount() == 0) {
                    Timber.d("Empty");

                    listView.addHeaderView(headerView, null, false);

                    Animation rotation = AnimationUtils.loadAnimation(context, R.anim.rotate);
                    rotation.setRepeatCount(Animation.INFINITE);
                    refresh.startAnimation(rotation);
                    mAdapter.setLastPosition(-1);
                } else {
                    Timber.d("Not empty");
                    listView.removeHeaderView(headerView);
                }

                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);

        mAdapter = new NsdAdapter(context, R.layout.list_item_nsd, mItems);
        listView.setAdapter(mAdapter);

        headerView = LayoutInflater.from(context).inflate(R.layout.list_item_nsd_empty, null);
        refresh = (ImageView) headerView.findViewById(R.id.anim_refresh);

        nsdService.setOnNsdChangeListener(new NsdService.OnNsdChangeListener() {
            @Override
            public void onServiceResolved(NsdServiceInfo info) {
                mItems.add(info);
                updateListView();
            }

            @Override
            public void onServiceLost(NsdServiceInfo info) {
                for (int i = 0; i < mItems.size(); i++) {
                    if (mItems.get(i).getServiceName().equalsIgnoreCase(info.getServiceName())) {
                        mItems.remove(i);
                    }
                }
                updateListView();
            }
        });

        updateListView();

        toolbar.setTitle(getContext().getString(R.string.select_device));
        toolbar.inflateMenu(R.menu.menu_nsd_activity);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_forward:
                        nsdService.stopDiscovery();

                        Intent intent = new Intent(context, ControllerActivity.class);
                        DVBHost host = DVBHost.Local();
                        intent.putExtra(Config.DVBHOST_KEY, host);
                        context.startActivity(intent);

                        break;
                }
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NsdServiceInfo nsd = mAdapter.getItem(position);
                nsdService.stopDiscovery();

                Intent intent = new Intent(context, ControllerActivity.class);
                DVBHost host = new DVBHost(nsd);
                intent.putExtra(Config.DVBHOST_KEY, host);

                context.startActivity(intent);
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        nsdService.discoverServices();
    }

    @Override
    protected void onDetachedFromWindow() {
        nsdService.stopDiscovery();

        super.onDetachedFromWindow();
    }


}
