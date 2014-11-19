package de.bennir.dvbviewerremote.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Channel
        implements Parcelable {
    public static final Parcelable.Creator<Channel> CREATOR = new Parcelable.Creator<Channel>() {
        public Channel createFromParcel(Parcel source) {
            return new Channel(source);
        }

        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };

    public int Id;
    public String Name;
    public String Group;
    public String ChannelId;
    public EpgInfo Epg;

    public Channel() {
    }

    private Channel(Parcel source) {
        Id = source.readInt();
        Name = source.readString();
        Group = source.readString();
        ChannelId = source.readString();
        Epg = source.readParcelable(EpgInfo.class.getClassLoader());
    }

    @Override
    public String toString() {
        return Id + ";" + Name + ";" + Group + ";" + ChannelId + ";" + Epg + ";";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(Id);
        out.writeString(Name);
        out.writeString(Group);
        out.writeString(ChannelId);
        out.writeParcelable(Epg, 0);
    }

    public static HashMap<String, List<Channel>> createChannelMap(List<Channel> mChannels) {
        String currentGroup = "";
        List<Channel> channelGroup = new ArrayList<Channel>();
        HashMap<String, List<Channel>> channelMap = new HashMap<String, List<Channel>>();

        for (Channel chan : mChannels) {
            // Channel Map Group->List<Channel>
            if (chan.Group.equals(currentGroup)) {
                channelGroup.add(chan);
            } else {
                if (currentGroup.equals("")) {
                    currentGroup = chan.Group;
                    channelGroup.add(chan);
                } else {
                    channelMap.put(currentGroup, channelGroup);
                    channelGroup = new ArrayList<Channel>();

                    currentGroup = chan.Group;
                    channelGroup.add(chan);
                }
            }
        }
        channelMap.put(currentGroup, channelGroup);

        return channelMap;
    }

    public static ArrayList<String> createChannelGroups(List<Channel> mChannels) {
        ArrayList<String> channelGroups = new ArrayList<String>();

        for (Channel chan : mChannels) {
            // Channel Group Names
            if (!channelGroups.contains(chan.Group)) {
                channelGroups.add(chan.Group);
            }
        }

        return channelGroups;
    }
}
