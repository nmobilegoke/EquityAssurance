package com.android.nmobile.equityassurance.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.android.nmobile.equityassurance.R;
import com.android.nmobile.equityassurance.utils.SessionManager;

/**
 * @author Dev Rupesh Saxena
 */

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                SessionManager.intialize(SplashActivity.this);
                if (SessionManager.isLoggedIn()) {
                    Intent _intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(_intent);
                    finish();
                    overridePendingTransition(R.anim.push_left_in_activity_start, R.anim.push_left_out_activity_start);

                } else {
                    Intent _intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(_intent);
                    finish();
                    overridePendingTransition(R.anim.push_left_in_activity_start, R.anim.push_left_out_activity_start);
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
