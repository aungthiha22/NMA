package com.rebook.nma.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;
import com.google.gson.JsonObject;
import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.adapter.HomeSlidePagerAdapter;
import com.rebook.nma.adapter.NewsAdapter;
import com.rebook.nma.adapter.PopularCourseAdapter;
import com.rebook.nma.model.Batch;
import com.rebook.nma.sync.SyncPostService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.rebook.nma.util.NetService;
import com.rebook.nma.widget.ZawgyiTextView;
import com.rebook.nma.widget.ZgToast;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.OkHttpClient;
import com.tuyenmonkey.mkloader.MKLoader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by Dell on 3/14/2019.
 */

public class HomeFragment extends Fragment {


    @BindView(R.id.scroll_home)ScrollView scrollHome;
    @BindView(R.id.loader_mk)MKLoader loading;
   //@BindView(R.id.home_progressBar)ProgressBar progressBar;
    @BindView(R.id.popular_course_recycle)RecyclerView popularRecycler;
    @BindView(R.id.new_recycle)RecyclerView newsRecycler;
    @BindView(R.id.hide_image)ImageView imgHide;
    @BindView(R.id.error_connection)ZawgyiTextView txtError;
    ViewPager pager;
    CircleIndicator indicator;
    HomeSlidePagerAdapter adapter;
    private static int currentPage = 0;
    OkHttpClient okHttpClient;
    SyncPostService syncPostService;
    PopularCourseAdapter popularCourseAdapter;
    NewsAdapter newsAdapter;
    ArrayList<Batch> list =new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        okHttpClient=new OkHttpClient();
        popularCourseAdapter = new PopularCourseAdapter(getActivity());
        newsAdapter = new NewsAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home, container, false);
        ButterKnife.bind(this, view);

        pager = view.findViewById(R.id.viewPager);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        CertificatePinner certificatePinner=new CertificatePinner.Builder()
                .add(Config.MAIN_URL)
                .build();
        okHttpClient.setCertificatePinner(certificatePinner);
        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(Config.MAIN_URL+Config.API)
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setClient(new OkClient(okHttpClient))
                .build();
        syncPostService=restAdapter.create(SyncPostService.class);

       // progressBar.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        scrollHome.setVisibility(View.GONE);

        syncPostService.getSlider(1, new Callback<JsonArray>() {
            @Override
            public void success(JsonArray jsonElements, Response response) {

                // list.clear();
                // list = new ArrayList<>();
                if (jsonElements.size() != 0) {
                    Toast.makeText(getActivity(), "This is activity", Toast.LENGTH_SHORT).show();
                    Gson gson = new Gson();
                    for (int i = 0; i < jsonElements.size(); i++) {
                        list.add(gson.fromJson(jsonElements.get(i), Batch.class));
                    }

                    adapter = new HomeSlidePagerAdapter(getActivity(), list);
                    //   Log.e("homeslider ", "______________ : " + sliderArray.size());
                    pager.setAdapter(adapter);
                    indicator.setViewPager(pager);
                    pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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
                                int pageCount = list.size();
                                if (currentPage == 0) {
                                    pager.setCurrentItem(pageCount - 1, false);
                                } else if (currentPage == pageCount - 1) {
                                    pager.setCurrentItem(0, false);
                                }
                            }
                        }
                    });
                    final Handler handler = new Handler();
                    final Runnable update = new Runnable() {
                        @Override
                        public void run() {
                            if (currentPage == list.size()) {
                                currentPage = 0;
                            }
                            pager.setCurrentItem(currentPage++, true);

                        }
                    };

                    Timer swipeTimer = new Timer();
                    swipeTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(update);
                        }
                    }, 3000, 3000);

                    // Toast.makeText(getActivity(), "slider Array " + sliderArray.size(), Toast.LENGTH_SHORT).show();
                } else {
                    imgHide.setVisibility(View.VISIBLE);
                 //   Toast.makeText(getActivity(), "array is null", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void failure(RetrofitError error) {
//                Toast.makeText(getActivity(), "Error is :\n" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        popularRecycler.setLayoutManager(gridLayoutManager);
        popularRecycler.setAdapter(popularCourseAdapter);
        popularCourseAdapter.notifyDataSetChanged();

        if (NetService.isInternetAvailable(getActivity())) {

            syncPostService.getCourses(1, new Callback<JsonObject>() {
                @Override
                public void success(JsonObject jsonObject, Response response) {

                    JsonArray jsonArrayBatch = jsonObject.get("batches").getAsJsonArray();
                    if (jsonArrayBatch.size() > 0) {
                        //progressBar.setVisibility(View.GONE);
                        loading.setVisibility(View.GONE);
                        scrollHome.setVisibility(View.VISIBLE);
                        list.clear();
                        for (int i = 0; i < jsonArrayBatch.size(); i++) {
                            Gson gson = new Gson();
                            list.add(gson.fromJson(jsonArrayBatch.get(i), Batch.class));
                        }
                        popularCourseAdapter.append(list);
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getActivity(), "Error \n"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            ZgToast zgToast = new ZgToast(getActivity());
            zgToast.setError();
            zgToast.setZgText(getResources().getString(R.string.no_connection));
            zgToast.show();
        }


        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(),1);
        gridLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        newsRecycler.setLayoutManager(gridLayoutManager1);
        newsRecycler.setAdapter(newsAdapter);
        newsAdapter.notifyDataSetChanged();

        syncPostService.getCourses(1, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {

                JsonArray jsonArrayBatch = jsonObject.get("batches").getAsJsonArray();
                list.clear();

                for (int i=0; i<jsonArrayBatch.size(); i++){
                    Gson gson = new Gson() ;
                    list.add(gson.fromJson(jsonArrayBatch.get(i),Batch.class));
                }
                newsAdapter.append(list);

            }

            @Override
            public void failure(RetrofitError error) {
              //  txtError.setText(error.getMessage());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loading.setVisibility(View.GONE);
                        txtError.setVisibility(View.VISIBLE);
                    }
                },2000);
            }
        });

        return view;
    }
}