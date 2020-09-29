package com.rebook.nma.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.rebook.nma.AnalyticsApplication;
import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.util.TinyDB;
import com.rebook.nma.widget.ZawgyiEditText;
import com.rebook.nma.widget.ZawgyiTextView;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Dell on 1/4/2019.
 */

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.login)ZawgyiTextView login;
    @BindView(R.id.register)ZawgyiTextView register;
    @BindView(R.id.register_first_name)ZawgyiEditText registerFirstName;
    @BindView(R.id.register_last_name)ZawgyiEditText registerLastName;
    @BindView(R.id.register_username) ZawgyiEditText registerUsername;
    @BindView(R.id.register_email)ZawgyiEditText registerEmail;
    @BindView(R.id.register_password)ZawgyiEditText registerPassword;
    @BindView(R.id.register_confirm_password)ZawgyiEditText registerConfirmPassword;

    TinyDB tinyDB;
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        ButterKnife.bind(this);
        AnalyticsApplication application = new AnalyticsApplication();
        Config.tracker = application.getDefaultTracker();
        tinyDB = new TinyDB(RegisterActivity.this) ;

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registerFirstName.getText().toString().length()<1){
                    registerFirstName.setError("Enter first name");
                    registerFirstName.requestFocus();
                    return;
                }
                if (registerLastName.getText().toString().length()<1){
                    registerLastName.setError("Enter last name");
                    registerLastName.requestFocus();
                    return;
                }
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });

    }
    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public void onResume() {
        super.onResume();
       // Config.tracker.setScreenName(Config.APP_NANE+"\tRegister Activity");
       // Config.tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}