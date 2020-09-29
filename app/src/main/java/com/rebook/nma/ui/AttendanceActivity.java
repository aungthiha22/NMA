package com.rebook.nma.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.adapter.AttendanceAdapter;
import com.rebook.nma.adapter.CustomAdapter;
import com.rebook.nma.listener.EndlessRecyclerViewScrollListener;
import com.rebook.nma.model.AttendanceCalendar;
import com.rebook.nma.model.Batch;
import com.rebook.nma.sync.SyncPostService;
import com.rebook.nma.util.NetService;
import com.rebook.nma.util.TinyDB;
import com.rebook.nma.widget.ZawgyiTextView;
import com.rebook.nma.widget.ZgToast;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.OkHttpClient;
import com.tuyenmonkey.mkloader.MKLoader;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class AttendanceActivity extends AppCompatActivity {

    public static final String ATTENDANCE_INTENT = "attendance_intent";

    @BindView(R.id.recyclerView)RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.loader_mk)MKLoader loading;
    @BindView(R.id.emptyView)ZawgyiTextView emptyView;
    @BindView(R.id.toolbar)Toolbar toolbar;


    SyncPostService syncPostService;
    AttendanceAdapter attendanceAdapter;
    OkHttpClient okHttpClient;
    ArrayList<Batch> list =new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    ZgToast zawgyiToast;
    boolean isRefresh,isLoadMore;
    String type;
    TinyDB tinyDB;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);
        ButterKnife.bind(this);
        ZawgyiTextView textView_toolbar = toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_ab_back_mtrl_am_alpha);

        tinyDB = new TinyDB(this);
        okHttpClient = new OkHttpClient();
        attendanceAdapter = new AttendanceAdapter(this);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        loading.setVisibility(View.VISIBLE);



        Log.e("attend is ", "__________\t" + tinyDB.getString(Config.STORE_TOKEN));
        type = getIntent().getStringExtra(ATTENDANCE_INTENT);
        textView_toolbar.setText(type);
        textView_toolbar.setText(getResources().getString(R.string.attendance));

        swipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.blue, R.color.red, R.color.blue);
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


        loadData(1);

        recyclerView.setAdapter(attendanceAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        // recyclerView.setItemAnimator(new DefaultItemAnimator());
        endlessRecyclerViewScrollListener=new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                isLoadMore=true;
                attendanceAdapter.showLoading(true);
                attendanceAdapter.notifyDataSetChanged();
                loadData(page);
            }
        };

        swipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.blue, R.color.red, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh=true;
                endlessRecyclerViewScrollListener.currentPage=0;
                endlessRecyclerViewScrollListener.previousTotalItemCount=0;
                endlessRecyclerViewScrollListener.loading=false;
                loadData(1);
            }
        });
        recyclerView.setOnScrollListener(endlessRecyclerViewScrollListener);

    }

    private void loadData(final int page) {
        if(NetService.isInternetAvailable(this)){
                attendanceAdapter.setType("attendance");
                syncPostService.getCurrentCourse(tinyDB.getString(Config.STORE_TOKEN),
                        new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, Response response) {
                        if (jsonObject != null) {
                            loading.setVisibility(View.GONE);
                           /* progressBar.setProgress(jsonObject.get("attendance_percentage").getAsInt());
                            progressPercentage.setText(jsonObject.get("attendance_percentage").getAsInt()+" %");*/
                            //JsonArray jsonArrayAttendance = jsonObject.get("attendances").getAsJsonArray();
                            JsonObject jsonArrayBatch = jsonObject.get("batches").getAsJsonObject();
                            JsonArray jsonArrayData = jsonArrayBatch.get("data").getAsJsonArray();

                            if (jsonArrayData.size() >0) {
                                list.clear();
                                for (int i = 0; i < jsonArrayData.size(); i++) {
                                    Gson gson = new Gson();
                                    list.add(gson.fromJson(jsonArrayData.get(i), Batch.class));
                                }
                                attendanceAdapter.replaceWith(list);
                                attendanceAdapter.notifyDataSetChanged();
                            }
                            else {
                                //This condition is else jsonArrayBatch is null
                                emptyView.setText(R.string.no_data);
                                emptyView.setVisibility(View.VISIBLE);

                            }
                            attendanceAdapter.showLoading(false);
                            swipeRefreshLayout.setRefreshing(false);
                        }else {

                            if(page==1){
                                recyclerView.setVisibility(View.GONE);
                                emptyView.setText("no internet connection");
                                emptyView.setVisibility(View.VISIBLE);
                            }

                            //this condition is else jsonObject is null
                            //progressBar.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.VISIBLE);
                            emptyView.setText(R.string.something_wrong);

                        }

                        attendanceAdapter.showLoading(false);
                        attendanceAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(AttendanceActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                        emptyView.setText(error.getMessage());
                        emptyView.setVisibility(View.VISIBLE);
                        //progressBar.setVisibility(View.GONE);
                        loading.setVisibility(View.GONE);

                    }
                });

        }
        else {
            //progressBar.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            zawgyiToast=new ZgToast(this);
            zawgyiToast.setZgText("no_internet");
            zawgyiToast.setError();
            zawgyiToast.show();
            if(!isRefresh || !isLoadMore){
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
