package de.bennir.dvbviewerremote.ui.nsd;

import android.content.Context;
import android.graphics.Typeface;
import android.net.nsd.NsdServiceInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.bennir.dvbviewerremote.R;

public class NsdAdapter extends ArrayAdapter<NsdServiceInfo> {
    private List<NsdServiceInfo> items;
    private int itemLayout;
    private Context mContext;

    public NsdAdapter(Context context, int resource, List<NsdServiceInfo> items) {
        super(context, resource, items);

        this.items = items;
        this.itemLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder; // to reference the child views for later actions

        if (v == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
            // cache view fields into the holder

            holder = new ViewHolder();
            holder.serviceName = (TextView) v.findViewById(R.id.service_name);
            holder.serviceHost = (TextView) v.findViewById(R.id.service_ip);
            // associate the holder with the view for later lookup
            v.setTag(holder);
        } else {
            // view already exists, get the holder instance from the view
            holder = (ViewHolder) v.getTag();
        }

        Typeface tf = Typeface.createFromAsset(mContext.getAssets(),"fonts/Roboto-Medium.ttf");
        holder.serviceName.setTypeface(tf);
        holder.serviceName.setText(items.get(position).getServiceName());
        holder.serviceHost.setText(items.get(position).getHost().getHostAddress() + ":" + items.get(position).getPort());

        return v;
    }

    static class ViewHolder {
        TextView serviceName;
        TextView serviceHost;
    }
}