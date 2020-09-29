package com.rebook.nma.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rebook.nma.AnalyticsApplication;
import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.adapter.ViewPagerAdapter;
import com.google.android.gms.analytics.HitBuilders;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;


/**
 * Created by Dell on 1/2/2019.
 */

public class SlideActivity extends AppCompatActivity {

    Button signUp;
    Button signIn;
    @BindView(R.id.connect_facebook)Button connectFacebook;

    ViewPager viewpager;
    PagerAdapter adapter;
    int[] img;
    private static int currentPage = 0;
    private static int NUM_PAGES = 5;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_activity);
        ButterKnife.bind(this);

        AnalyticsApplication application = new AnalyticsApplication();
        Config.tracker = application.getDefaultTracker();

        img = new int[]{R.drawable.course,
                R.drawable.course,
                R.drawable.course,
                R.drawable.ic_menu_camera};

        viewpager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(SlideActivity.this, img);
        viewpager.setAdapter(adapter);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewpager);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrollStateChanged(int state) {
               // Toast.makeText(getApplicationContext(), "context changed", Toast.LENGTH_SHORT).show();

                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    int pageCount = img.length;
                    if (currentPage == 0) {
                        viewpager.setCurrentItem(pageCount - 1, false);
                    } else if (currentPage == pageCount - 1) {
                        viewpager.setCurrentItem(0, false);
                    }
                }
            }
        });
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewpager.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 3000, 3000);




        connectFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SlideActivity.this, "connect facebook", Toast.LENGTH_SHORT).show();
            }
        });

        signIn = findViewById(R.id.sing_in);
        signUp = findViewById(R.id.sign_up);
        
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SlideActivity.this, "this is sign in", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SlideActivity.this,LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SlideActivity.this, "this is sign up", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SlideActivity.this,RegisterActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        finish();
    }
    @Override
    public void onResume() {
        super.onResume();
        Config.tracker.setScreenName(Config.APP_NAME+"\tSlide Activity");
        Config.tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
