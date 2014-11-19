package de.bennir.dvbviewerremote.model;

public class DVBMenuItem {
    private String Title;
    private int Icon;

    public DVBMenuItem(String Title, int Icon) {
        this.Title = Title;
        this.Icon = Icon;
    }

    public int getIcon() {
        return Icon;
    }

    public String getTitle() {
        return Title;
    }
}