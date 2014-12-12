package de.bennir.dvbviewerremote.ui.nsd;

import android.content.Context;
import android.net.nsd.NsdServiceInfo;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.bennir.dvbviewerremote.R;
import de.bennir.dvbviewerremote.ui.misc.BetterViewAnimator;
import timber.log.Timber;

public class NsdView extends BetterViewAnimator {
    @InjectView(android.R.id.list) ListView listView;
    @InjectView(R.id.toolbar) Toolbar toolbar;

    private Context context;

    private final NsdAdapter mAdapter;
    private List<NsdServiceInfo> mItems = new ArrayList<>();

    @Inject NsdService nsdService;

    public NsdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        mAdapter = new NsdAdapter(context, R.layout.list_item_nsd, mItems);

        nsdService.setOnNsdChangeListener(new NsdService.OnNsdChangeListener() {
            @Override public void onServiceResolved(NsdServiceInfo info) {

            }

            @Override public void onServiceLost(NsdServiceInfo info) {
                for (int i = 0; i < mItems.size(); i++) {
                    if (mItems.get(i).getServiceName().equalsIgnoreCase(info.getServiceName())) {
                        mItems.remove(i);

                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);

        NsdComponent component = NsdComponent.Initializer.init(context);
        component.inject(this);

        Timber.d("NsdService: " + nsdService.toString());
//        nsdService = component.service();

        listView.setAdapter(mAdapter);

        toolbar.setTitle(getContext().getString(R.string.select_device));
        toolbar.inflateMenu(R.menu.menu_nsd_activity);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_refresh:
                        Toast.makeText(getContext(), "Refresh", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.menu_forward:
                        Toast.makeText(getContext(), "Skip", Toast.LENGTH_LONG).show();
                        break;
                }
                return true;
            }
        });
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        //TODO: start NsdService
    }

    @Override protected void onDetachedFromWindow() {
        //TODO: stop NsdService

        super.onDetachedFromWindow();
    }
}
