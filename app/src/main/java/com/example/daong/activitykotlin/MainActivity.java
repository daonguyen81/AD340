package com.example.daong.activitykotlin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final String TAG = "States";
    public static final String EXTRA_TEXT = "com.example.daong.activitykotlin.EXTRA_TEXT";
    MaterialSearchView materialSearchView;
    String[] list;
    SharedPreferences mySharedPreferences;
    private static final String mypreferences = "mypref";
    private static final String text = "inputText";
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edittext);
        loadText();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        list = new String[]{"AD340", "Android", "Mobile App", "Google", "Programming", "Android Developer"};
        materialSearchView = (MaterialSearchView) findViewById(R.id.mysearch);
        materialSearchView.setSuggestions(list);
        materialSearchView.setEllipsize(true);
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Here create your filtering
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //change if typing
                return false;
            }
        });

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });


        Button z_button = (Button) findViewById(R.id.z_button);
        z_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openZombieList();
            }
        });
        Log.d(TAG, "MainActivity: onCreate()");
    }

    public void sendTextToSecondActivity(View v) {
        editText = (EditText) findViewById(R.id.edittext);
        String text = editText.getText().toString();

        if(isValidInput(text)) {
            save(text.trim());
            openActivity2(text);
        } else {
            Toast.makeText(MainActivity.this, "Input can't be empty!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isValidInput(String text) {
        text = text.trim();
        if(text.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void save(String myText) {
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(text, myText);
        editor.commit();
    }

    public void  loadText(){
        mySharedPreferences = getSharedPreferences(mypreferences, Context.MODE_PRIVATE);
        if (mySharedPreferences.contains(text)){
            editText.setText(mySharedPreferences.getString(text, ""));
        }
    }

    private void openZombieList() {
        Intent intent = new Intent(this, ZombieList.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (materialSearchView.isSearchOpen()) {
            materialSearchView.closeSearch();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_option, menu);

        MenuItem item = menu.findItem(R.id.search);
        materialSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_setting) {
            Toast toast = Toast.makeText(MainActivity.this, "This is setting option menu!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 10);
            toast.show();
        }
        if(id == R.id.search) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.about_us) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
        }

        if (id == R.id.main_list){
            Intent intent = new Intent(this,ZombieList.class);
            if(isNetworkAvailable(MainActivity.this)) //returns true if internet available
            {
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(MainActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 16);
                toast.show();
            }
        }

        if (id == R.id.camera_list){
            Intent intent = new Intent(this,CameraList.class);
            if(isNetworkAvailable(MainActivity.this)) //returns true if internet available
            {
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(MainActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 16);
                toast.show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openActivity2(String str) {

        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra(EXTRA_TEXT, str);
        startActivity(intent);
    }

    public void onDisplayToast1(View v) {
        Toast toast = Toast.makeText(MainActivity.this, "This is TOAST 1!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 16);
        toast.show();
    }

    public void onDisplayToast2(View v) {
        Toast toast = Toast.makeText(MainActivity.this, "This is TOAST 2!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 16);
        toast.show();
    }

    public void onDisplayToast3(View v) {

        Toast toast = Toast.makeText(MainActivity.this, "This is TOAST 3!", Toast.LENGTH_SHORT);

        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 16);

        toast.show();

    }

    public boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void  onRestart() {
        super.onRestart();
        Log.d(TAG, "MainActivity: onRestart()");
    }

    @Override
    protected void  onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity: onStart()");
    }

    @Override
    protected void  onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity: onResume()");
    }

    @Override
    protected void  onPause() {
        super.onPause();
        Log.d(TAG, "MainActivity: onPause()");
    }

    @Override
    protected void  onStop() {
        super.onStop();
        Log.d(TAG, "MainActivity: onStop()");
    }

    @Override
    protected void  onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity: onDestroy()");
    }
}
