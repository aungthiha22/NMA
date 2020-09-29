package com.rebook.nma.ui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.model.Announcement;
import com.rebook.nma.sync.SyncPostService;
import com.rebook.nma.widget.ZawgyiTextView;
import com.rebook.nma.widget.ZgToast;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.OkHttpClient;
import com.tuyenmonkey.mkloader.MKLoader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class AnnouncementDetailActivity extends AppCompatActivity {
    public static final String ANNOUNCEMENT_ID = "announcement_id";
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xOTIuMTY4LjguMjBcL2FwaVwvYXV0aGVudGljYXRlIiwiaWF0IjoxNTYyMDM5OTMzLCJleHAiOjE1NjIyNTU5MzMsIm5iZiI6MTU2MjAzOTkzMywianRpIjoiWlZteEVjU05GSWRyb3ZxRSIsInN1YiI6MTksInBydiI6IjEzZThkMDI4YjM5MWYzYjdiNjNmMjE5MzNkYmFkNDU4ZmYyMTA3MmUifQ.gPkv2EQjpMwB73169WraWPtmA1MhIYYIPkpPP_xjb58";

    @BindView(R.id.announcement_name)ZawgyiTextView txtAnnocementName;
    @BindView(R.id.announcement_date)ZawgyiTextView txtAnnoncementDate;
    @BindView(R.id.announcement_download_cardView)CardView cardViewDownload;
    @BindView(R.id.announcement_desc)ZawgyiTextView txtAnnouncementDesc;
    @BindView(R.id.detail_loading)MKLoader loader;
    @BindView(R.id.announcement_scroll)ScrollView scrollView;

    OkHttpClient okHttpClient;
    SyncPostService syncPostService;
    String announcementId;
    String download_url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcement_detail);
        ButterKnife.bind(this);

        loader.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        announcementId = getIntent().getStringExtra(ANNOUNCEMENT_ID);
        Toast.makeText(this, "id is "+announcementId, Toast.LENGTH_SHORT).show();

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

        syncPostService.getAnnouncementsDetail(announcementId, token, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loader.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                    }
                },1000);
                Toast.makeText(AnnouncementDetailActivity.this, "success", Toast.LENGTH_SHORT).show();
                JsonObject jsonObjectAnnouncement = jsonObject.get("announcement").getAsJsonObject();
                Gson gson = new Gson();
                Announcement announcement = gson.fromJson(jsonObjectAnnouncement,Announcement.class);
                txtAnnocementName.setText(announcement.getName());
                txtAnnoncementDate.setText(announcement.getExpiredDate());
                txtAnnouncementDesc.setText(announcement.getDescription());

                download_url = Config.MAIN_URL+"/storage/"+announcement.getFileName();
                cardViewDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DownloadFile().execute(download_url);
                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(AnnouncementDetailActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private class DownloadFile extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        private String fileName;
        private String folder;
        private boolean isDownloaded;

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(AnnouncementDetailActivity.this);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                // getting file length
                int lengthOfFile = connection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

                //Extract file name from URL
                fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1, f_url[0].length());

                //Append timestamp to file name
                fileName = timestamp + "_" + fileName;

                //External directory path to save file
                folder = Environment.getExternalStorageDirectory() + File.separator + "mna/";
                //Create androiddeft folder if it does not exist
                File directory = new File(folder);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Output stream to write file
                OutputStream output = new FileOutputStream(folder + fileName);

                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();
                // closing streams
                output.close();
                input.close();
                return "Downloaded at: " + folder + fileName;

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return "Something went wrong";
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String message) {
            // dismiss the dialog after the file was downloaded
            this.progressDialog.dismiss();
            // Display File path after downloading
            ZgToast zgToast = new ZgToast(getApplicationContext());
            zgToast.setZgText(message);
            zgToast.show();
        }
    }


}
