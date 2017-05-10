package com.android.nmobile.equityassurance.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.nmobile.equityassurance.R;
import com.android.nmobile.equityassurance.utils.ApplicationConstants;

/**
 * @author Dev Rupesh Saxena
 */

public class ContactUsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        //set title of toolbar
        getSupportActionBar().setTitle(ApplicationConstants.CONTACT_US);

        //HomeAsUpEnabled
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.push_left_out_activity_finish, R.anim.push_left_in_activity_finish);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_out_activity_finish, R.anim.push_left_in_activity_finish);
    }
}
