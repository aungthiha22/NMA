package com.rebook.nma.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.util.TinyDB;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 1/2/2019.
 */

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.exit_ads)ImageView imgExit;
    @BindView(R.id.ads_img)ImageView imgAds;
    TinyDB tinyDB;
    boolean login;
    String flag = "null";

    private Handler updateHandler =new Handler();
    Runnable runnable =new Runnable() {
        @Override
        public void run() {
           // goSlider();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        tinyDB = new TinyDB(StartActivity.this);
        login = tinyDB.getBoolean(Config.LOG_IN);
        updateHandler.postDelayed(runnable,2000);

        if (flag == "not_null"){
            startActivity(new Intent(StartActivity.this,MainActivity.class));
        }else {
            imgAds.setImageResource(R.drawable.course);
        }

        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        return;
    }
}
