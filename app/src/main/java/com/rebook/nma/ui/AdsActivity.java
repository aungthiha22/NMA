package com.rebook.nma.ui;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rebook.nma.R;
import com.rebook.nma.sync.SyncPostService;
import com.rebook.nma.util.NetService;
import com.rebook.nma.util.TinyDB;
import com.rebook.nma.widget.ZgToast;
import com.squareup.okhttp.OkHttpClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdsActivity extends AppCompatActivity {


    public static final String ADS_ID="adsID";
    public static final String TARGET_URL="targetUrl";
    public static final String IMG_TYPE="type";
    public static final String IMG="image";
    public static final String NAME="name";
    @BindView(R.id.ads_img)
    ImageView ads;
    @BindView(R.id.exit_ads)ImageView exit;
    @BindView(R.id.progressBarForAds)
    ProgressBar progressBar;
    SyncPostService syncPostService;
    OkHttpClient okHttpClient;
    ZgToast zgToast;
    SQLiteDatabase sqLiteDatabase;
    String imgType,adsImgUrl,targetUrl,adsID,adsIDFromDB="0",imgTypeFromDB,name,status,playStoreDownload,directDownloadStr;
    Cursor cursor;
    byte[] imgByteArrayFromDB;
    String urlForWebView;
    AlertDialog dialog;
    boolean isBackPressed;
    TinyDB tinyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_ads);

        ButterKnife.bind(this);
        sqLiteDatabase=SQLiteDatabase.openDatabase("data/data/com.rebook.nma/messagesDB",null,SQLiteDatabase.CREATE_IF_NECESSARY);
        //sqLiteDatabase.execSQL("drop table if exists adsTable");
        sqLiteDatabase.execSQL("create table if not exists adsTable(id integer PRIMARY KEY AUTOINCREMENT," +
                "name text," +
                "adsID text," +
                "image BLOB," +
                "targetUrl text," +
                "type text)");
        //sqLiteDatabase.close();
        okHttpClient=new OkHttpClient();

        cursor=sqLiteDatabase.rawQuery("select * from adsTable order by id desc limit 1",
                null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                adsIDFromDB=cursor.getString(cursor.getColumnIndex(ADS_ID));
                imgTypeFromDB=cursor.getString(cursor.getColumnIndex(IMG_TYPE));
                imgByteArrayFromDB=cursor.getBlob(cursor.getColumnIndex(IMG));
                //Log.i("ads id from db",adsIDFromDB);
            }
        }
        Log.e("Testing","count is ___________ "+cursor.getCount());
        Log.e("Testing","count is ___________ "+imgTypeFromDB);
        tinyDB=new TinyDB(this);

        if(NetService.isInternetAvailable(this)){
            progressBar.setVisibility(View.GONE);
            status = "on";
            adsID = "111";
            adsImgUrl = "https://www.imyanmarhouse.com/pictures/mobile_ad/1569237355.jpg";
            targetUrl = "https://www.imyanmarhouse.com/promo/m-suite";
            name = "၀ယ္ယူသူ Owner Book ရရွိမည့္ ဗႏၶဳလတံတားအနီး M Suite Residence အေရာင္းျပပြဲ";


            if (status.equalsIgnoreCase("on")) {
                exit.setVisibility(View.VISIBLE);
                if (!adsIDFromDB.equalsIgnoreCase(adsID)) {

                    Log.e("Testing","______________"+adsIDFromDB);
                    Log.e("Testing","______________"+adsID);

                    urlForWebView = targetUrl;
                    /*Glide.with(TestingActivity.this)
                            .load(imgByteArrayFromDB).into(ads);*/
                   /* Glide.with(AdsActivity.this)
                            .load("https://www.imyanmarhouse.com/pictures/mobile_ad/1569237355.jpg")
                            .into(ads);*/
                   ads.setImageResource(R.drawable.course);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(TARGET_URL, targetUrl);
                    sqLiteDatabase.update("adsTable", contentValues, "id=" + adsID, null);
                } else {
                    urlForWebView = targetUrl;
                }
            }

        }
        ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdsActivity.this, "url is "+urlForWebView, Toast.LENGTH_SHORT).show();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdsActivity.this,MainActivity.class);
                overridePendingTransition(0,0);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AdsActivity.this,MainActivity.class);
        overridePendingTransition(0,0);
        startActivity(intent);
        finish();
    }
}
