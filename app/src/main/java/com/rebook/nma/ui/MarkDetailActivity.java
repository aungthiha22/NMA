package com.rebook.nma.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.model.Marks;
import com.rebook.nma.sync.SyncPostService;
import com.rebook.nma.util.NetService;
import com.rebook.nma.util.TinyDB;
import com.rebook.nma.widget.ZawgyiTextView;
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

public class MarkDetailActivity extends AppCompatActivity {

    public static final String MARK_ID="mark_id";
    @BindView(R.id.mark_attendance)ZawgyiTextView txtMarkAttendance;
    @BindView(R.id.mark_discussion)ZawgyiTextView txtMarkDiscussion;
    @BindView(R.id.mark_presentation)ZawgyiTextView txtMarkPresentation;
    @BindView(R.id.mark_assignment)ZawgyiTextView txtMarkAssignment;
    @BindView(R.id.mark_exam)ZawgyiTextView txtMarkExam;
    @BindView(R.id.mark_final_result)ZawgyiTextView txtMarkResult;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.scroll_mark)
    ScrollView scrollView;
    @BindView(R.id.loader_mk)
    MKLoader mkLoader;

    OkHttpClient okHttpClient;
    SyncPostService syncPostService;
    String markId;
    TinyDB tinyDB ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mark_detail_activity);
        ButterKnife.bind(this);
        ZawgyiTextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        toolbar_title.setText(getResources().getString(R.string.mark_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_ab_back_mtrl_am_alpha);
        markId = getIntent().getStringExtra(MARK_ID);
       // Log.e("mark Detail","+_________________"+markId);
        mkLoader.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        txtMarkAttendance.setText("attendance");
        txtMarkDiscussion.setText("discussion");
        txtMarkPresentation.setText("presentation");
        txtMarkAssignment.setText("assignment");
        txtMarkExam.setText("exam");
        txtMarkResult.setText("final result");

        tinyDB = new TinyDB(this);
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
        if (NetService.isInternetAvailable(this)) {
            syncPostService.getMarkDetail(markId,tinyDB.getString(Config.STORE_TOKEN), new Callback<JsonObject>() {
                @Override
                public void success(JsonObject jsonObject, Response response) {
                    mkLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    Toast.makeText(MarkDetailActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    JsonObject jsonObjectMark = jsonObject.get("mark").getAsJsonObject();
                    Gson gson = new Gson();
                    Marks marks = gson.fromJson(jsonObjectMark, Marks.class);

                    txtMarkAttendance.setText(marks.getAttendance() + "");
                    txtMarkDiscussion.setText(marks.getGroupDiscussion()+"");
                    txtMarkPresentation.setText(marks.getGroupPresentation()+"");
                    txtMarkAssignment.setText(marks.getAssignment()+"");
                    txtMarkExam.setText(marks.getExam()+"");
                    txtMarkResult.setText(marks.getFinalResult()+"");
                    Log.e("","_______\t"+marks.getAssignment());


                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(MarkDetailActivity.this, "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(this, "no internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
