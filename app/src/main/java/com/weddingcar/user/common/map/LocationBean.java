package com.weddingcar.user.common.map;

import java.io.Serializable;

public class LocationBean implements Serializable{

    private String title;
    private String content;
    private double lon;
    private double lat;
    private String adCode;
    private String adName;

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "LocationBean{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", adCode='" + adCode + '\'' +
                ", adName='" + adName + '\'' +
                '}';
    }
}
