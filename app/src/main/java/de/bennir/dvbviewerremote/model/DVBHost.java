package de.bennir.dvbviewerremote.model;


import android.net.nsd.NsdServiceInfo;
import android.os.Parcel;
import android.os.Parcelable;

public class DVBHost
        implements Parcelable {
    public static final Parcelable.Creator<DVBHost> CREATOR = new Parcelable.Creator<DVBHost>() {
        public DVBHost createFromParcel(Parcel source) {
            return new DVBHost(source);
        }

        public DVBHost[] newArray(int size) {
            return new DVBHost[size];
        }
    };

    public String Name = "";
    public String Ip = "";
    public String Port = "";

    public DVBHost() {
    }

    public DVBHost(String Name, String Ip, String Port) {
        this.Name = Name;
        this.Ip = Ip;
        this.Port = Port;
    }

    public DVBHost(NsdServiceInfo nsd) {
        this.Name = nsd.getServiceName();
        this.Ip = nsd.getHost().getHostAddress();
        this.Port = String.valueOf(nsd.getPort());
    }

    private DVBHost(Parcel source) {
        Name = source.readString();
        Ip = source.readString();
        Port = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(Name);
        out.writeString(Ip);
        out.writeString(Port);
    }

    public String getUrl() {
        return "http://" + Ip + ":" + Port;
    }

    @Override
    public String toString() {
        return Name + " (" + Ip + ":" + Port + ")";
    }
}
