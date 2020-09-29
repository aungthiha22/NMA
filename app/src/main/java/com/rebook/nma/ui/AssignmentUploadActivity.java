package com.rebook.nma.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;
import com.andexert.library.RippleView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.model.Assignment;
import com.rebook.nma.model.Batch;
import com.rebook.nma.sync.SyncPostService;
import com.rebook.nma.util.FilePath;
import com.rebook.nma.util.TinyDB;
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
import retrofit.mime.TypedFile;

public class AssignmentUploadActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.choice_file_rippleView)RippleView rippleViewChoiceFile;
    @BindView(R.id.file_name)ZawgyiTextView txtFileName;
    @BindView(R.id.upLoadCardView)CardView cardViewUploadFile;
    @BindView(R.id.cardViewDownload)CardView cardViewDownloadFile;
    @BindView(R.id.assignment_description)ZawgyiTextView txtAssignmentDesc;
    @BindView(R.id.assignment_file)ZawgyiTextView txtAssignmentFile;
    @BindView(R.id.assignment_scroll)ScrollView scrollView;
    @BindView(R.id.loader_mk)MKLoader loader;
    ZawgyiTextView toolbar_title;
    TinyDB tinyDB ;

    //Declaring views
    private static final int PICK_FILE_REQUEST = 1;
    private static final String TAG = MainActivity.class.getSimpleName();
    private String selectedFilePath;
     private String SERVER_URL;

    ProgressDialog dialog;
    OkHttpClient okHttpClient = new OkHttpClient();
    SyncPostService syncPostService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignment_detail);
        ButterKnife.bind(this);
        toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        toolbar_title.setText("Upload Assignment");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_ab_back_mtrl_am_alpha);

        tinyDB = new TinyDB(AssignmentUploadActivity.this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading file...");
        loader.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
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
        syncPostService.getAssignmentDetail("2", tinyDB.getString(Config.STORE_TOKEN), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                JsonObject jsonObjectAss = jsonObject.get("assignments").getAsJsonObject();
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       loader.setVisibility(View.GONE);
                       scrollView.setVisibility(View.VISIBLE);
                   }
               },1000);
                Gson gson = new Gson();
                Assignment assignment = gson.fromJson(jsonObjectAss, Assignment.class);
                txtAssignmentDesc.setText(assignment.getDescription());
                txtAssignmentFile.setText(assignment.getQuestionFile());
                SERVER_URL = Config.MAIN_URL+"/storage/"+ assignment.getQuestionFile();
               // Log.e("Testing Url ","_________"+SERVER_URL);

                cardViewDownloadFile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DownloadFile().execute(SERVER_URL);
                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(AssignmentUploadActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //this is setOnRippleCompleteListener for choice file
        rippleViewChoiceFile.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                if (shouldAskPermissions()){
                    askPermissions();
                }
                showFileChooser();

            }
        });

        cardViewUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFilePath != null){
                    uploadFile(selectedFilePath);
                }else {
                    Toast.makeText(AssignmentUploadActivity.this, "Please Choice a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("*/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE_REQUEST);
    }

    public void uploadFile(final String selectedFilePath){

        dialog.show();
        File photoFile = new File(selectedFilePath);// image will be your real path
        String mimeType = getMimeType(selectedFilePath);
        TypedFile photoTypedFile;
        if (mimeType != null) {
            photoTypedFile = new TypedFile(mimeType, photoFile);
            Log.e("Testing Activity ","not null"+photoTypedFile.toString());
        } else {
            photoTypedFile = new TypedFile("image/jpg", photoFile);
            Log.e("Testing Activity ","null "+photoTypedFile.toString());
        }
        // Toast.makeText(this, "file name is "+photoTypedFile.toString(), Toast.LENGTH_SHORT).show();
        if (!photoFile.isFile()){
            dialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtFileName.setText("Source File Doesn't Exist: " + selectedFilePath);
                }
            });
        }else{
            syncPostService.getUploadAssignment(photoTypedFile, "dfdsfdf"
                    , tinyDB.getString(Config.STORE_TOKEN), new Callback<JsonObject>() {
                        @Override
                        public void success(JsonObject jsonPrimitive, Response response) {
                            dialog.dismiss();
                            ZgToast zgToast = new ZgToast(AssignmentUploadActivity.this);
                            zgToast.setZgText("file upload success");
                            zgToast.show();
                            Log.e("Testing activity","photo type is --________ success ");
                            startActivity(new Intent(AssignmentUploadActivity.this,AssignmentUploadActivity.class));
                            finish();

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.e("Testing activity","photo type is -________ error "+error.getMessage());
                            dialog.dismiss();
                        }
                    });
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == PICK_FILE_REQUEST){
                if(data == null){
                    //no data present
                    return;
                }

                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePath.getPath(this,selectedFileUri);
                Log.i(TAG,"Selected File Path:" + selectedFilePath);

                if(selectedFilePath != null && !selectedFilePath.equals("")){
                    txtFileName.setText(selectedFilePath);

                }else{
                    Toast.makeText(this,"Cannot upload file to server",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    /**
     * Async Task to download file from URL
     */
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
            this.progressDialog = new ProgressDialog(AssignmentUploadActivity.this);
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
                    Log.d(TAG, "Progress: " + (int) ((total * 100) / lengthOfFile));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}