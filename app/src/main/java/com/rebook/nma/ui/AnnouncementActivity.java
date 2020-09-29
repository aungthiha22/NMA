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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.adapter.AnnouncementAdapter;
import com.rebook.nma.adapter.MarkAdapter;
import com.rebook.nma.listener.EndlessRecyclerViewScrollListener;
import com.rebook.nma.model.Announcement;
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

public class AnnouncementActivity extends AppCompatActivity {


    public static final String MARK_INTENT = "mark_intent";

    @BindView(R.id.recyclerView)RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.loader_mk)MKLoader loading;
    @BindView(R.id.emptyView)ZawgyiTextView emptyView;
    @BindView(R.id.toolbar)Toolbar toolbar;
    SyncPostService syncPostService;
    AnnouncementAdapter announcementAdapter;
    OkHttpClient okHttpClient;
    ArrayList<Announcement> list =new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    ZgToast zawgyiToast;
    boolean isRefresh,isLoadMore;
    String type;
    TinyDB tinyDB ;

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
        announcementAdapter = new AnnouncementAdapter(this);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        loading.setVisibility(View.VISIBLE);
        type = getIntent().getStringExtra(MARK_INTENT);

        if(type.equals("announcement")){
            textView_toolbar.setText(getResources().getString(R.string.announcement));
        }else if (type.equals("attendance")){
            textView_toolbar.setText(getResources().getString(R.string.attendance));
        }
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.blue, R.color.green, R.color.blue);
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

        recyclerView.setAdapter(announcementAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        // recyclerView.setItemAnimator(new DefaultItemAnimator());
        endlessRecyclerViewScrollListener=new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                isLoadMore=true;
                announcementAdapter.showLoading(true);
                announcementAdapter.notifyDataSetChanged();
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
            if(type.equals("")){/*
                announcementAdapter.setType("attendance");
                syncPostService.getCurrentCourse(tinyDB.getString(Config.STORE_TOKEN), new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, Response response) {
                        if (jsonObject != null) {
                            //progressBar.setVisibility(View.GONE);
                            loading.setVisibility(View.GONE);

                            JsonObject jsonArrayBatch = jsonObject.get("batches").getAsJsonObject();
                            JsonArray jsonArrayData = jsonArrayBatch.get("data").getAsJsonArray();
                            if (jsonArrayData.size() >0) {
                                list.clear();
                                for (int i = 0; i < jsonArrayData.size(); i++) {
                                    Gson gson = new Gson();
                                    list.add(gson.fromJson(jsonArrayData.get(i), Announcement.class));
                                }
                                if (page!=1) {
                                    announcementAdapter.replaceWith(list);
                                    announcementAdapter.notifyDataSetChanged();
                                }else {
                                    announcementAdapter.append(list);
                                    announcementAdapter.notifyDataSetChanged();
                                }
                            }
                            else {
                                //This condition is else jsonArrayBatch is null
                                emptyView.setText(R.string.no_data);
                                emptyView.setVisibility(View.VISIBLE);

                            }
                            announcementAdapter.showLoading(false);
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

                        announcementAdapter.showLoading(false);
                        announcementAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        emptyView.setText(error.getMessage());
                        emptyView.setVisibility(View.VISIBLE);
                        //progressBar.setVisibility(View.GONE);
                        loading.setVisibility(View.GONE);

                    }
                });
          */  }else if(type.equals("announcement")){
                Toast.makeText(this, "This is working", Toast.LENGTH_SHORT).show();
                announcementAdapter.setType("announcement");
                syncPostService.getAnnouncement(tinyDB.getString(Config.STORE_TOKEN), new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, Response response) {
                        if (jsonObject != null) {
                            //progressBar.setVisibility(View.GONE);
                            loading.setVisibility(View.GONE);

                            JsonArray jsonArrayAnnouncement = jsonObject.get("announcements").getAsJsonArray();
                            Log.e("anno size ","_____________"+jsonArrayAnnouncement.size()+"");
                            if (jsonArrayAnnouncement.size() >0) {
                                list.clear();
                                for (int i = 0; i < jsonArrayAnnouncement.size(); i++) {
                                    Gson gson = new Gson();
                                    list.add(gson.fromJson(jsonArrayAnnouncement.get(i), Announcement.class));
                                }
                                announcementAdapter.replaceWith(list);
                                announcementAdapter.notifyDataSetChanged();
                            }
                            else {
                                //This condition is else jsonArrayBatch is null
                                emptyView.setText(R.string.no_data);
                                emptyView.setVisibility(View.VISIBLE);

                            }
                            announcementAdapter.showLoading(false);
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

                        announcementAdapter.showLoading(false);
                        announcementAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(AnnouncementActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        emptyView.setText(error.getMessage());
                        emptyView.setVisibility(View.VISIBLE);
                        //progressBar.setVisibility(View.GONE);
                        loading.setVisibility(View.GONE);

                    }
                });
            }

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
