package com.rebook.nma.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;
import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.adapter.ExpandableListAdapter;
import com.rebook.nma.model.Batch;
import com.rebook.nma.sync.SyncPostService;
import com.rebook.nma.widget.ZawgyiTextView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.squareup.okhttp.OkHttpClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by Dell on 3/25/2019.
 */

public class AboutUsActivity extends AppCompatActivity{

    //@BindView(R.id.aboutUs_recycle)RecyclerView recyclerView;
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    @BindView(R.id.list_expandable)ExpandableListView expandableListView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    ZawgyiTextView toolBar_textView;

    OkHttpClient okHttpClient;
    SyncPostService syncPostService;
    ArrayList arrayList ;
    //AboutUsAdapter aboutUsAdapter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us );
        ButterKnife.bind(this);

        toolBar_textView = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_ab_back_mtrl_am_alpha);

        okHttpClient=new OkHttpClient();
       //aboutUsAdapter = new AboutUsAdapter(AboutUsActivity.this);
        prepareListData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listDataChild);
        expandableListView.setAdapter(listAdapter);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Config.MAIN_URL+Config.API)
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setClient(new OkClient(okHttpClient)).build();


        syncPostService = restAdapter.create(SyncPostService.class);

        syncPostService.getProductLatest(1, new Callback<JsonArray>() {
            @Override
            public void success(JsonArray jsonElements, Response response) {
                Toast.makeText(AboutUsActivity.this, "success , ok , success", Toast.LENGTH_SHORT).show();
                arrayList = new ArrayList() ;
                for (int i=0; i<jsonElements.size(); i++){
                    Gson gson = new Gson();
                    arrayList.add(gson.fromJson(jsonElements.get(i), Batch.class));
                }
                Log.e("AboutUsActivity","array size is ________"+String.valueOf(arrayList.size()));
              //  aboutUsAdapter.append(arrayList);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(AboutUsActivity.this, "error \t"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }
}
