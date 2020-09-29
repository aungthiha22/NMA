package com.rebook.nma.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.model.Batch;
import com.rebook.nma.sync.SyncPostService;
import com.rebook.nma.util.NetService;
import com.rebook.nma.widget.ZawgyiTextView;
import com.rebook.nma.widget.ZgToast;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.OkHttpClient;
import com.tuyenmonkey.mkloader.MKLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class EventDetailActivity extends AppCompatActivity {

    public static final String EVENT_ID = "event_id";
    public static final String HAS = "EventDetail";

    @BindView(R.id.event_linearLayout)
    LinearLayout layout;
    @BindView(R.id.event_detail_title)ZawgyiTextView txtEventDetailTitle;
    @BindView(R.id.event_detail_time)ZawgyiTextView txtEventDetailTime;
    @BindView(R.id.event_detail_address)ZawgyiTextView txtEventDetailAddress;
    @BindView(R.id.event_detail_description)ZawgyiTextView txtEventDetailDesc;
    @BindView(R.id.event_detail_image)ImageView imgEventDetailImage;
    @BindView(R.id.event_detail_loading)MKLoader loader;
    @BindView(R.id.toolbar)Toolbar toolbar;

    SyncPostService syncPostService;
    OkHttpClient okHttpClient ;

    String event_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail_activity);
        ButterKnife.bind(this);
        ZawgyiTextView textView_toolbar = toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_ab_back_mtrl_am_alpha);

        textView_toolbar.setText("Event Detail");
        event_id = getIntent().getStringExtra(EVENT_ID);
        okHttpClient = new OkHttpClient();
        CertificatePinner certificatePinner=new CertificatePinner.Builder()
                .add(Config.MAIN_URL)
                .build();
        okHttpClient.setCertificatePinner(certificatePinner);
        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(Config.MAIN_URL+ Config.API)
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setClient(new OkClient(okHttpClient))
                .build();
        syncPostService=restAdapter.create(SyncPostService.class);

        layout.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);

        if (NetService.isInternetAvailable(this)) {
            syncPostService.getEventDetail(event_id, new Callback<JsonObject>() {
                @Override
                public void success(JsonObject jsonObject, Response response) {
                    layout.setVisibility(View.VISIBLE);
                    loader.setVisibility(View.GONE);

                    Gson gson = new Gson();
                    Batch batch = gson.fromJson(jsonObject, Batch.class);
                    txtEventDetailTitle.setText(batch.getName());
                    txtEventDetailTime.setText(batch.getStartTime() + "\t-\t" + batch.getEndTime());
                    txtEventDetailAddress.setText(batch.getAddress());
                    txtEventDetailDesc.setText(batch.getDescription());
                    Log.e(HAS, "image url ______ \t" + batch.getImageUrl());

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
        else {
            ZgToast zgToast = new ZgToast(this);
            zgToast.setError();
            zgToast.setText(getResources().getString(R.string.no_connection));
            zgToast.show();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
