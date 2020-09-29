package com.rebook.nma.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.gson.JsonObject;
import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.sync.SyncPostService;
import com.rebook.nma.util.NetService;
import com.rebook.nma.util.TinyDB;
import com.rebook.nma.widget.ZawgyiCheckBox;
import com.rebook.nma.widget.ZawgyiEditText;
import com.rebook.nma.widget.ZawgyiTextView;
import com.rebook.nma.widget.ZgToast;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.OkHttpClient;

import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;

import javax.security.auth.login.LoginException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by Dell on 1/4/2019.
 */

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public static final int REQ_CODE = 9001;
    private static final int REQ_SIGN_IN_REQUIRED = 55664;

    @BindView(R.id.login_register)ZawgyiTextView loginRegister;
    @BindView(R.id.login_login)ZawgyiTextView loginLogin;
    @BindView(R.id.login_username)ZawgyiEditText loginUsername;
    @BindView(R.id.login_password)ZawgyiEditText loginPassword;
    @BindView(R.id.facebook_image)ImageView facebookImageView;
    @BindView(R.id.facebook_login)LoginButton loginWithFacebook;
    @BindView(R.id.google)ImageView googleImageView;
    @BindView(R.id.google_login)SignInButton loginWithGoogle;
    @BindView(R.id.check_login)ZawgyiCheckBox cbLogin;

    TinyDB tinyDB ;
    CallbackManager callbackManager;
    GoogleApiClient googleApiClient;
    OkHttpClient okHttpClient = new OkHttpClient();
    SyncPostService syncPostService;
    String mAccountName ;
    ProgressDialog progressDialog ;

    //this is search Image url for testing
   /* String image=  "https://randomuser.me/api/portraits/women/93.jpg";
    String image2="https://randomuser.me/api/portraits/women/79.jpg ";
    String image3="https://randomuser.me/api/portraits/women/56.jpg";
    String image4="https://randomuser.me/api/portraits/women/44.jpg";
    String image5="https://randomuser.me/api/portraits/women/82.jpg";
    String image6= "https://randomuser.me/api/portraits/lego/3.jpg";
    String image7= "https://randomuser.me/api/portraits/women/60.jpg";
    String image8= "https://randomuser.me/api/portraits/women/32.jpg";
    String image9="https://randomuser.me/api/portraits/women/67.jpg";
*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        tinyDB = new TinyDB(LoginActivity.this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");

        if (tinyDB.getBoolean(Config.STORE_LOGIN_CHECK)){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
        //this is for google login
        String serverClientId = getString(R.string.client_id);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                .requestIdToken(serverClientId)
                .requestServerAuthCode(serverClientId)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(LoginActivity.this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginWithFacebook.setReadPermissions(Arrays.asList("public_profile","email"));

        facebookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "-This is facebook image", Toast.LENGTH_SHORT).show();
                Log.e("Login activity","This is working");
                onClickFacebookButton(v);
            }
        });
        googleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,REQ_CODE);
            }
        });


        loginWithFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String accessToken = loginResult.getAccessToken().getToken();
                Toast.makeText(LoginActivity.this, "access token is "+accessToken, Toast.LENGTH_SHORT).show();
                Log.e("Login activity","token is "+accessToken);
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);

            }

            @Override
            public void onCancel() {
                Log.e("Login activity","token is cancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("Login activity","token is error");
            }
        });


        loginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });
        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add(Config.MAIN_URL)
                .build();
        okHttpClient.setCertificatePinner(certificatePinner);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Config.MAIN_URL+Config.API)
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setClient(new OkClient(okHttpClient))
                .build();
        syncPostService = restAdapter.create(SyncPostService.class);

        loginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetService.isInternetAvailable(LoginActivity.this)) {
                    if (loginUsername.getText().toString().length() < 1) {
                        loginUsername.setError("Enter username");
                        loginUsername.requestFocus();
                        return;
                    }
                    if (loginPassword.getText().toString().length() < 1) {
                        loginPassword.setError("Enter password");
                        loginPassword.requestFocus();
                        return;
                    }

                    progressDialog.show();
                    syncPostService.getLogin(loginUsername.getText().toString(),
                            loginPassword.getText().toString(),
                            new Callback<JsonObject>() {
                        @Override
                        public void success(JsonObject jsonObject, Response response) {
                            try {
                                tinyDB.clear();
                                String token = jsonObject.get("token").getAsString();
                                JsonObject jsonObjectUser = jsonObject.get("user").getAsJsonObject();
                                String user_id = String.valueOf(jsonObjectUser.get("id").getAsInt());
                                String name = jsonObjectUser.get("name").getAsString();
                                String email = jsonObjectUser.get("email").getAsString();
                                int instructor = jsonObjectUser.get("instructor").getAsInt();

                                String image_url = "";
                                Log.e("token is ", "__________\t" + token);
                                //Log.e("Name is ", "__________\t" + name);
                                //Log.e("Email is ", "__________\t" + email);
                                //Log.e("instructor is ", "__________\t" + instructor);

                                tinyDB.putStringMethod(Config.STORE_ID,user_id);
                                tinyDB.putStringMethod(Config.STORE_TOKEN,token);
                                tinyDB.putStringMethod(Config.STORE_NAME,name);
                                Log.e("token is ", "__________\t" + tinyDB.getString(Config.STORE_TOKEN));
                                tinyDB.putStringMethod(Config.STORE_EMAIL,email);
                                tinyDB.putStringMethod(Config.STORE_IMG_URL,image_url);
                                tinyDB.putInt(Config.STORE_INSTRUCTOR,instructor);
                                if (cbLogin.isChecked()) {
                                    tinyDB.putBoolean(Config.STORE_LOGIN_CHECK, true);
                                }else {
                                    tinyDB.putBoolean(Config.STORE_LOGIN_CHECK,false);
                                }

                                progressDialog.dismiss();
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();

                            }catch (NullPointerException e){
                                progressDialog.dismiss();
                                Log.e("Login Activity ","NullPointerException");
                                ZgToast zgToast = new ZgToast(LoginActivity.this);
                                zgToast.setError();
                                zgToast.setZgText("email or Password Incorrect");
                                zgToast.show();
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(LoginActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    ZgToast zgToast = new ZgToast(LoginActivity.this);
                    zgToast.setZgText(getString(R.string.no_connection));
                    zgToast.show();

                }

            }
        });

    }
    public void onClickFacebookButton(View view) {
        if (view == facebookImageView) {
            loginWithFacebook.performClick();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(this,RegisterActivity.class));
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
      //  Config.tracker.setScreenName(Config.APP_NANE+"\tLogin Activity");
       // Config.tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
            //todo this is account name declare
            new RetrieveTokenTask().execute(mAccountName);

        }

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    public void handleResult(GoogleSignInResult result){
        if (result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            if (account.getPhotoUrl() == null){

                Log.e("LogActivity","url is null");
            }
            else{
                Log.e("LogActivity","url is not null"+account.getPhotoUrl());

            }
            Log.e("TestingActivity : ","name\t_________"+account.getDisplayName());
            Log.e("TestingActivity : ","email\t_________"+account.getEmail());
            Log.e("TestingActivity : ","name\t_________"+account.getPhotoUrl());
            mAccountName = account.getEmail();

        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class RetrieveTokenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String accountName = params[0];
            String scopes = "oauth2:profile email";
            String token = null;
            try {
                token = GoogleAuthUtil.getToken(getApplicationContext(), accountName, scopes);

                Log.e("DoInBackground","__________"+token);
            } catch (IOException e) {

            } catch (UserRecoverableAuthException e) {
                startActivityForResult(e.getIntent(), REQ_SIGN_IN_REQUIRED);
            } catch (GoogleAuthException e) {
                Log.e("TAG", e.getMessage());
            }catch (NullPointerException e){
                Log.e("TAG", e.getMessage());
            }
            return token;
        }
    }
}
