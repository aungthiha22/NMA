package com.rebook.nma.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.rebook.nma.AnalyticsApplication;
import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.util.TinyDB;
import com.rebook.nma.widget.ZgToast;
import com.google.android.gms.analytics.HitBuilders;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)Toolbar toolbar;

    String path = "data/data/com.a.myapplication/database";
    SQLiteDatabase db;
    boolean doubleBackToExitPressedOnce = false;
    private static final String EMAIL = "email";
    TinyDB tinyDB ;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle(getResources().getString(R.string.app_name));
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new HomeFragment(),"item").commit();
                    return true;

                case R.id.navigation_notification:
                    toolbar.setTitle("Notification");
                    for (Fragment fragment : getSupportFragmentManager().getFragments()){
                        if (fragment !=null && fragment.getTag() != null && !(fragment.getTag().equals("notification"))){
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    GeneralListFragment.getInstance("notification"),"notification").commit();
                        }
                    }
                    return true;

                case R.id.navigation_search:
                    toolbar.setTitle("Search");
                    for (Fragment fragment : getSupportFragmentManager().getFragments()){
                        if (fragment !=null && fragment.getTag() != null && !(fragment.getTag().equals("search"))){
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new SearchFragment(),"search").commit();
                        }
                    }
                    //startActivity(new Intent(MainActivity.this,SearchActivity.class));

                    return true;
               case R.id.navigation_course:
                   toolbar.setTitle("Course");
                   for (Fragment fragment : getSupportFragmentManager().getFragments()){
                       if (fragment !=null && fragment.getTag() != null && !(fragment.getTag().equals("course"))){
                           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                   GeneralListFragment.getInstance("course"),"course").commit();
                       }
                   }
                    return true;

                case R.id.navigation_profile:
                    toolbar.setTitle("Profile");
                    for (Fragment fragment : getSupportFragmentManager().getFragments()){
                        if (fragment !=null && fragment.getTag() != null && !(fragment.getTag().equals("profile"))){
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new ProfileFragment(),"profile").commit();
                        }
                    }

                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AnalyticsApplication application = new AnalyticsApplication();
        Config.tracker = application.getDefaultTracker();
        tinyDB = new TinyDB(MainActivity.this);

        // This is for testing api

        toolbar.setTitle(getResources().getString(R.string.app_name));
        printKeyHash();
        //db.execSQL("drop table if exists vehicle");
        //printKeyHash();

        setSupportActionBar(toolbar);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //item.setShiftingMode(false);
                // item.setPadding(0, 15, 0, 0);
                //item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment(),"item").commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.navigation_event){
            Intent intent = new Intent(MainActivity.this,ItemListActivity.class);
            intent.putExtra(ItemListActivity.INTENT_VALUE,"Events");
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            startActivity(intent);

        }else if (id == R.id.navigation_blog){
           /* intent.putExtra(ItemListActivity.INTENT_VALUE,"Blog");
            overridePendingTransition(0,0);
            startActivity(intent);*/

        }else if (id == R.id.navigation_job){
            Intent intent = new Intent(MainActivity.this,ItemListActivity.class);
            intent.putExtra(ItemListActivity.INTENT_VALUE,"Jobs");
            overridePendingTransition(0,0);
            startActivity(intent);
            Toast.makeText(this, "this is navigation_background __ job", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.navigation_about){
            Toast.makeText(this, "this is navigation_background __ about", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.navigation_contact){

            Toast.makeText(this, "this is navigation_background __ contact", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.navigation_logout){
            tinyDB.clear();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        else if (doubleBackToExitPressedOnce)
        {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        ZgToast zgToast = new ZgToast(MainActivity.this);
        zgToast.setZgText(getString(R.string.to_exit));
        zgToast.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        },2000);
    }

    private void printKeyHash(){
       try {
           PackageInfo info = getPackageManager().getPackageInfo("com.rebook.nma",PackageManager.GET_SIGNATURES);
           for (Signature signature:info.signatures){
               MessageDigest md = MessageDigest.getInstance("SHA");
               md.update(signature.toByteArray());
               Log.e("keyHash __________", Base64.encodeToString(md.digest(),Base64.DEFAULT));
           }

       }catch (PackageManager.NameNotFoundException e){

       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
       }
    }
   /* @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.setting:

                return true;
            case R.id.search:
                startActivity(new Intent(MainActivity.this,AboutUsActivity.class));
                overridePendingTransition(0,0);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        search.setVisible(true);//
        MenuItem setting = menu.findItem(R.id.setting);
        setting.setVisible(false);


        return true;
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Config.tracker.setScreenName(Config.APP_NAME+"\tHome Activity");
        Config.tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


}
