package com.rebook.nma.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.model.Batch;
import com.rebook.nma.model.Course;
import com.rebook.nma.sync.SyncPostService;
import com.rebook.nma.util.NetService;
import com.rebook.nma.util.Utils;
import com.rebook.nma.widget.ZawgyiTextView;
import com.rebook.nma.widget.ZgToast;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.OkHttpClient;
import com.tuyenmonkey.mkloader.MKLoader;

import org.xml.sax.XMLReader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by Dell on 6/3/2019.
 */

public class CourseDetailActivity extends AppCompatActivity{

    public static final String IMG_URL = "image_url";
    public static final String COURSE_ID = "course_id";

    @BindView(R.id.detail_scroll)ScrollView scrollView;
    @BindView(R.id.detail_loading)MKLoader loading;
    @BindView(R.id.course_detail_title)ZawgyiTextView txtCourseTitle;
    @BindView(R.id.course_detail_image)ImageView imgCourseDetail;
    @BindView(R.id.course_detail_student_count)ZawgyiTextView txtStudentCount;
    @BindView(R.id.course_detail_review)ZawgyiTextView txtReview;
    @BindView(R.id.course_detail_hours)ZawgyiTextView txtHours;
    @BindView(R.id.course_detail_date)ZawgyiTextView txtDate;
    @BindView(R.id.course_detail_fees)ZawgyiTextView txtFees;
    @BindView(R.id.course_detail_time)ZawgyiTextView txtTime;
    @BindView(R.id.course_detail_instructor_image)ImageView txtInstructorImage;
    @BindView(R.id.course_detail_instructor_name)ZawgyiTextView txtInstructorName;
    @BindView(R.id.course_detail_description)ZawgyiTextView txtDescription;
    @BindView(R.id.course_detail_certificate)ZawgyiTextView txtCertification;
    @BindView(R.id.see_curriculum)ZawgyiTextView txtSeeCurriculum;
    @BindView(R.id.see_reviews)ZawgyiTextView txtSeeReviews;
    @BindView(R.id.toolbar)Toolbar toolbar;

    String image_url , course_id;
    OkHttpClient okHttpClient;
    SyncPostService syncPostService;

    ZawgyiTextView toolBar_textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail_activity);
        ButterKnife.bind(this);

        okHttpClient = new OkHttpClient();
        toolBar_textView = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        toolBar_textView.setText("Course Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_ab_back_mtrl_am_alpha);
        imgCourseDetail.setVisibility(View.VISIBLE);

        course_id = String.valueOf(getIntent().getIntExtra(COURSE_ID,0));
        image_url = getIntent().getStringExtra(IMG_URL);

        /*Log.e("Course Detail","id__________\t"+course_id);
        Log.e("Course Detail","image url__________\t"+image_url);
        */
        scrollView.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

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

        if (NetService.isInternetAvailable(CourseDetailActivity.this)) {
            syncPostService.getCourseDetail(course_id, new Callback<JsonObject>() {
                @Override
                public void success(JsonObject jsonObject, Response response) {
                    scrollView.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    Glide.with(CourseDetailActivity.this).load(Config.MAIN_URL + "storage/" + getIntent().getStringExtra(IMG_URL))
                            .override(Utils.getPx(CourseDetailActivity.this, 200),
                                    Utils.getPx(CourseDetailActivity.this, 180))
                            .into(imgCourseDetail);

                    JsonArray jsonArrayBatch = jsonObject.get("batch").getAsJsonArray();
                    JsonObject jsonObjectBatch = jsonArrayBatch.get(0).getAsJsonObject();
                    JsonObject jsonObjectCourse = jsonObjectBatch.get("course").getAsJsonObject();
                    Gson gson = new Gson();
                    Course course = gson.fromJson(jsonObjectCourse, Course.class);
                    Log.e("Name ", "name__________\t" + Config.MAIN_URL+ "storage/" + getIntent().getStringExtra(IMG_URL));
                    Log.e("description ", "description__________\t" + course.getDescription());

                    txtCourseTitle.setText(course.getName());
                    txtFees.setText(course.getAmount()+"\tက်ပ္");
                    //Html.fromHtml(getString(R.string.service_title),null,new UlTagHandler())
                    //txtDescription.setText(Html.fromHtml(course.getDescription(),null,new UlTagHandler()));
                    txtDescription.setText(Html.fromHtml(course.getDescription()));

                    txtHours.setText(course.getDuration()+"");
                    txtCertification.setText(course.getCertificate());


                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(CourseDetailActivity.this, "error " + error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }else {
            ZgToast zgToast = new ZgToast(CourseDetailActivity.this);
            zgToast.setZgText(getResources().getString(R.string.no_connection));
            zgToast.show();
        }
        txtSeeCurriculum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CourseDetailActivity.this, "this is curriculum", Toast.LENGTH_SHORT).show();
            }
        });
        txtSeeReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CourseDetailActivity.this, "this is see Reviews", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
    public static class UlTagHandler implements Html.TagHandler{
        boolean isOL;
        int a=1;
        @Override
        public void handleTag(boolean opening, String tag, Editable output,
                              XMLReader xmlReader) {
            if(tag.equals("ul") && !opening) output.append("\n");
            if(tag.equals("ol") && !opening) {
                output.append("\n");
                a=1;
            }
            if(tag.equals("ol") && opening){
                isOL=true;
            }
            if(isOL){
                if(tag.equals("li") && opening) output.append("\n\t"+ a++ +".\t");
            }
            else {
                if(tag.equals("li") && opening) output.append("\n\t•\t");
            }
        }
    }
    
}
