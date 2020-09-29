package com.rebook.nma.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.gson.JsonObject;
import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.adapter.CustomAdapter;
import com.rebook.nma.listener.EndlessRecyclerViewScrollListener;
import com.rebook.nma.listener.HidingScrollListener;
import com.rebook.nma.model.Batch;
import com.rebook.nma.sync.SyncPostService;
import com.rebook.nma.util.NetService;
import com.rebook.nma.widget.ZawgyiTextView;
import com.rebook.nma.widget.ZgToast;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
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

/**
 * Created by YDN on 7/3/2017.
 */
public class GeneralListFragment extends Fragment {
    @BindView(R.id.recyclerView)RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)SwipeRefreshLayout swipeRefreshLayout;
    //@BindView(R.id.category_progressBar)ProgressBar progressBar;
    @BindView(R.id.loader_mk)MKLoader loading;
    @BindView(R.id.emptyView)ZawgyiTextView emptyView;
    SyncPostService syncPostService;
    CustomAdapter customAdapter;
    OkHttpClient okHttpClient;
    ArrayList<Batch> list =new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    ZgToast zawgyiToast;
    boolean isRefresh,isLoadMore;
    String type ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        okHttpClient=new OkHttpClient();
        customAdapter=new CustomAdapter(getContext());
    }
    public static GeneralListFragment getInstance(String type){
        GeneralListFragment generalListFragment=new GeneralListFragment();
        Bundle bundle=new Bundle();
        bundle.putString("type",type);
        generalListFragment.setArguments(bundle);
        return generalListFragment;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.general_list,null,false);
        ButterKnife.bind(this,view);

       // GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        //gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        //progressBar.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        type = getArguments().getString("type");
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

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        // recyclerView.setItemAnimator(new DefaultItemAnimator());
        endlessRecyclerViewScrollListener=new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                isLoadMore=true;
                customAdapter.showLoading(true);
                customAdapter.notifyDataSetChanged();
                loadData(page);
            }
        };
        /*recyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
            }

            @Override
            public void onShow() {
                mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
            }
        });*/
        recyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {

            }

            @Override
            public void onShow() {

            }
        });
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
        return view;
    }

    private void loadData(final int page) {
        if(NetService.isInternetAvailable(getActivity())){
            if(getArguments().getString("type").equals("course")){
                customAdapter.setType("course");
                syncPostService.getAllCourses(1, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, Response response) {
                        if (jsonObject != null) {
                            //progressBar.setVisibility(View.GONE);
                            loading.setVisibility(View.GONE);
                            JsonObject objectBatches = jsonObject.get("batches").getAsJsonObject();
                            JsonArray jsonArrayData = objectBatches.get("data").getAsJsonArray();
                            if (jsonArrayData.size() >0) {
                                list.clear();
                                for (int i = 0; i < jsonArrayData.size(); i++) {
                                    Gson gson = new Gson();
                                    list.add(gson.fromJson(jsonArrayData.get(i), Batch.class));
                                }
                                customAdapter.replaceWith(list);
                                customAdapter.notifyDataSetChanged();
                            }
                            else {
                                //This condition is else jsonArrayBatch is null
                                emptyView.setText(R.string.no_data);
                                emptyView.setVisibility(View.VISIBLE);

                            }
                            customAdapter.showLoading(false);
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

                        customAdapter.showLoading(false);
                        customAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        emptyView.setText(error.getMessage());
                        emptyView.setVisibility(View.VISIBLE);
                        //progressBar.setVisibility(View.GONE);
                        loading.setVisibility(View.GONE);

                    }
                });

                /*syncPostService.getProductLatest(page, new Callback<JsonArray>() {
                    @Override
                    public void success(JsonArray jsonElements, Response response) {
                        syncPostService.getProductLatest(1, new Callback<JsonArray>() {
                            @Override
                            public void success(JsonArray jsonArray, Response response) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "success to success", Toast.LENGTH_SHORT).show();
                                // list = new ArrayList<>();
                                for (int i=0; jsonArray.size()>i; i++){
                                    Gson gson = new Gson();
                                    list.add(gson.fromJson(jsonArray.get(i),Batch.class));
                                }
                                customAdapter.replaceWith(list);
                                Log.e("General list","\t"+String.valueOf(list.size()));
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });
                    }
                    @Override
                    public void failure(RetrofitError error) {

                    }
                });*/

            }
            else if (getArguments().getString("type").equals("notification")) {
                customAdapter.setType("notification");
                syncPostService.getCourses(1, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, Response response) {
                        if (jsonObject != null) {
                            //progressBar.setVisibility(View.GONE);
                            loading.setVisibility(View.GONE);
                            JsonArray jsonArrayBatch = jsonObject.get("batches").getAsJsonArray();
                            if (jsonArrayBatch.size() >0) {
                                list.clear();
                                for (int i = 0; i < jsonArrayBatch.size(); i++) {
                                    Gson gson = new Gson();
                                    list.add(gson.fromJson(jsonArrayBatch.get(i), Batch.class));
                                }
                                customAdapter.replaceWith(list);
                                customAdapter.notifyDataSetChanged();
                                customAdapter.showLoading(false);


                            }

                            else {
                                //This condition is else jsonArrayBatch is null
                                emptyView.setText(R.string.no_data);
                                emptyView.setVisibility(View.VISIBLE);

                            }
                            customAdapter.showLoading(false);
                            swipeRefreshLayout.setRefreshing(false);
                        }else {
                            //this condition is else jsonObject is null
                            //progressBar.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.VISIBLE);
                            emptyView.setText(R.string.something_wrong);

                        }

                        customAdapter.showLoading(false);
                        customAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void failure(RetrofitError error) {
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
            zawgyiToast=new ZgToast(getActivity());
            zawgyiToast.setZgText("no_internet");
            zawgyiToast.setError();
            zawgyiToast.show();
            if(!isRefresh || !isLoadMore){
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            }
        }
    }
}
