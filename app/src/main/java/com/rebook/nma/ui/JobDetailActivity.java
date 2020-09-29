package com.rebook.nma.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.model.Batch;
import com.rebook.nma.sync.SyncPostService;
import com.rebook.nma.widget.ZawgyiTextView;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.OkHttpClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class JobDetailActivity extends AppCompatActivity {
    public static final String JOB_INTENT = "job_intent";
    @BindView(R.id.job_title)
    ZawgyiTextView txtJobTitle;


    OkHttpClient okHttpClient;
    SyncPostService syncPostService;

    String job_intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_detail_activity);
        ButterKnife.bind(this);

        job_intent = getIntent().getStringExtra(JOB_INTENT);

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

        syncPostService.getjobDetail(job_intent, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                Toast.makeText(JobDetailActivity.this, "success", Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                Batch batch = gson.fromJson(jsonObject,Batch.class);
                txtJobTitle.setText(batch.getName());
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(JobDetailActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
