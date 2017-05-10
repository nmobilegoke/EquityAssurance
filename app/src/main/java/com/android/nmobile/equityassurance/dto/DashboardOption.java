package com.android.nmobile.equityassurance.dto;

import android.content.Intent;

import java.io.Serializable;

/**
 * @author Dev Rupesh Saxena
 */

public class DashboardOption implements Serializable {
    private String title;
    private int image;
    private Intent intent;

    public DashboardOption(String title, int image, Intent intent) {
        this.title = title;
        this.image = image;
        this.intent = intent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
