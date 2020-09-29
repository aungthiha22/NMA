package com.rebook.nma.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.adapter.SearchListViewAdapter;
import com.rebook.nma.model.Course;
import com.rebook.nma.sync.SyncPostService;
import com.rebook.nma.util.NetService;
import com.rebook.nma.widget.ZgToast;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.OkHttpClient;
import java.security.cert.Certificate;
import java.util.ArrayList;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    // Declare Variables
    private ListView list;
    private SearchListViewAdapter adapter;
    private SearchView editsearch;
    private String[] moviesList;
    /* private String[] description;*/
    public static ArrayList<Course> movieNamesArrayList = new ArrayList<Course>();
    OkHttpClient okHttpClient = new OkHttpClient();
    SyncPostService syncPostService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        // Locate the ListView in listview_main.xml
        list = (ListView) findViewById(R.id.listview);
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

        if (NetService.isInternetAvailable(SearchActivity.this)) {


        }else{
            ZgToast zgToast = new ZgToast(SearchActivity.this);
            zgToast.setZgText(getResources().getString(R.string.no_connection));
            zgToast.show();
        }

        // Pass results to ListViewAdapter Class
        adapter = new SearchListViewAdapter(this);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SearchActivity.this, movieNamesArrayList.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SearchActivity.this,CourseDetailActivity.class);
                intent.putExtra(CourseDetailActivity.IMG_URL,"1");
                intent.putExtra(CourseDetailActivity.COURSE_ID,10);
                startActivity(intent);

            }
        });
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
