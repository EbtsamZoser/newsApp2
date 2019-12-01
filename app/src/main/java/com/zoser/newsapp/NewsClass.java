package com.zoser.newsapp;

import android.graphics.Bitmap;

public class NewsClass {

    private String webTitle;
    private String type;
    private String webUrl;
    private String timing;
    private String section;
    private Bitmap image;

    public NewsClass(String webTitle, String type, String webUrl, String timing, String section, Bitmap image) {

        this.webTitle = webTitle;
        this.type = type;
        this.webUrl = webUrl;
        this.timing = timing;
        this.section = section;
        this.image = image;

    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getType() {
        return type;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getTiming() {
        return timing;
    }

    public String getSection() {
        return section;
    }

    public Bitmap getImage() {
        return image;
    }
}