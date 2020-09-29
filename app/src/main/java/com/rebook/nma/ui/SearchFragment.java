package com.rebook.nma.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.adapter.NewsAdapter;
import com.rebook.nma.adapter.PopularCourseAdapter;
import com.rebook.nma.adapter.SearchListViewAdapter;
import com.rebook.nma.model.Course;
import com.rebook.nma.sync.SyncPostService;
import com.rebook.nma.util.NetService;
import com.rebook.nma.widget.ZawgyiTextView;
import com.rebook.nma.widget.ZgToast;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.OkHttpClient;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Dell on 4/28/2019.
 */

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {


    @BindView(R.id.listview)ListView listView;
    @BindView(R.id.search) SearchView editsearch;
    // Declare Variables

    private SearchListViewAdapter adapter;

    private String[] moviesList;
    /* private String[] description;*/
    public static ArrayList<Course> movieNamesArrayList = new ArrayList<Course>();
    OkHttpClient okHttpClient = new OkHttpClient();
    SyncPostService syncPostService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        okHttpClient = new OkHttpClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_activity, container, false);
        ButterKnife.bind(this, view);

        // Locate the ListView in listview_main.xml

        movieNamesArrayList = new ArrayList<>();

        //generate sample data
        moviesList = new String[4];
        moviesList = new String[]{"Xmen", "Titanic", "Captain America",
                "Iron man", "Rocky", "Transporter", "Lord of the rings", "The jungle book",
                "Tarzan","Cars","Shreck","Xmen", "Titanic","Captain America",
                "Iron man", "Rocky", "Transporter", "Lord of the rings", "The jungle book",
                "Tarzan","Cars","Shreck"};

        Log.e("movies list ","_______\t"+moviesList.toString());

        for (int i = 0; i < moviesList.length; i++) {
            Course course = new Course(moviesList[i]);
            // Binds all strings into an array
            movieNamesArrayList.add(course);
        }

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

        if (NetService.isInternetAvailable(getActivity())) {

        }else{
            ZgToast zgToast = new ZgToast(getActivity());
            zgToast.setZgText(getResources().getString(R.string.no_connection));
            zgToast.show();
        }

        // Pass results to ListViewAdapter Class
        adapter = new SearchListViewAdapter(getActivity());

        // Binds the Adapter to the ListView
        listView.setAdapter(adapter);

        // Locate the EditText in listview_main.xml

        editsearch.setOnQueryTextListener(this);




        return view;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }
}
