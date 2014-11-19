package de.bennir.dvbviewerremote.model;

import android.os.Parcel;
import android.os.Parcelable;

public class EpgInfo
        implements Parcelable {
    public static final Parcelable.Creator<EpgInfo> CREATOR = new Parcelable.Creator<EpgInfo>() {
        @Override
        public EpgInfo createFromParcel(Parcel source) {
            return new EpgInfo(source);
        }

        @Override
        public EpgInfo[] newArray(int size) {
            return new EpgInfo[size];
        }
    };

    public String Time;
    public long EndTime;
    public String ChannelId;
    public String Title;
    public String Desc;
    public String Duration;
    public String Date;

    public EpgInfo() {}

    private EpgInfo(Parcel source) {
        Time = source.readString();
        EndTime = source.readLong();
        ChannelId = source.readString();
        Title = source.readString();
        Desc = source.readString();
        Duration = source.readString();
        Date = source.readString();
    }

    @Override
    public String toString() {
        return Time + "" + EndTime + "" + ChannelId + "" + Title + "" + Desc + "" + Duration + "" + Date + "";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(Time);
        out.writeLong(EndTime);
        out.writeString(ChannelId);
        out.writeString(Title);
        out.writeString(Desc);
        out.writeString(Duration);
        out.writeString(Date);
    }
}
