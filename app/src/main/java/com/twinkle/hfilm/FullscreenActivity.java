package com.twinkle.hfilm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.loopj.android.image.SmartImageView;
import com.twinkle.hfilm.inter.Spider;
import com.twinkle.hfilm.java.Util;
import com.twinkle.hfilm.service.SpiderService;



public class FullscreenActivity extends AppCompatActivity {

    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private Button dummy_button;
    private CountDownTimer countDownTimer;
    private SmartImageView svw_full;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

            svw_full.setImageUrl(new Spider().get_url("href_img"), R.drawable.logo_fir);

        }
    };

    private final View.OnClickListener mDelayHideTouchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            finish();
            countDownTimer.cancel();
            startActivity(new Intent(FullscreenActivity.this, MainActivity.class));

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);


        try {
            init();
            apply();
            service();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void init() {
        mContentView = findViewById(R.id.fullscreen_content);
        dummy_button = findViewById(R.id.dummy_button);
        svw_full = findViewById(R.id.svw_full);

    }

    private void apply() throws Exception {
        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                dummy_button.setText(String.format(getResources().getString(R.string.time), l / 1000));
            }
            @Override
            public void onFinish() {
                startActivity(new Intent(FullscreenActivity.this, MainActivity.class));
                finish();
            }
        }.start();
        dummy_button.setOnClickListener(mDelayHideTouchListener);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hide();
    }


    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mHideHandler.postDelayed(mHidePart2Runnable, 200);
    }

   private void service(){
       Intent intent = new Intent(FullscreenActivity.this, SpiderService.class);
       startService(intent);

   }


}
