package com.android.nmobile.equityassurance.dto;

import android.content.Intent;

/**
 * @author Dev Rupesh Saxena
 */

public class InsuranceCategorie {
    private String title;
    private Intent intent;

    public InsuranceCategorie(String title, Intent intent) {
        this.title = title;
        this.intent = intent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
