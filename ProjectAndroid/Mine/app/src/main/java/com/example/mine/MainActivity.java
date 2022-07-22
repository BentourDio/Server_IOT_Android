package com.example.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.example.mine.databinding.Frag1Binding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mine.ui.main.SectionsPagerAdapter;
import com.example.mine.databinding.ActivityMainBinding;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    static String mytime;
    public static BufferedReader reader1;
    public static BufferedReader reader2;

    String [] curr;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView( R.layout.activity_main );

            checkInternet();

        //toolbar for the dropDown Menu
            Toolbar toolbar = findViewById( R.id.toolbar );
            setSupportActionBar( toolbar );


            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
            final ViewPager viewPager = (ViewPager) findViewById( R.id.view_pager );
            viewPager.setAdapter(sectionsPagerAdapter);

            TabLayout tabs = (TabLayout) findViewById( R.id.tabs );
            tabs.addTab( tabs.newTab().setText( "Automatic Location" ) );
            tabs.addTab( tabs.newTab().setText( "Manual Location" ) );
            tabs.setTabGravity( TabLayout.GRAVITY_FILL );

            viewPager.setOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( tabs ) );

            tabs.setOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected( TabLayout.Tab tab ) {
                    viewPager.setCurrentItem( tab.getPosition() );
                }

                @Override
                public void onTabUnselected( TabLayout.Tab tab ) {

                }

                @Override
                public void onTabReselected( TabLayout.Tab tab ) {

                }
            } );
            if(getIntent().getBooleanExtra("EXIT",false))
            {
                finish();
            }
        }

    //Dropdown menu settings (up-right)
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate( R.menu.my_menu,menu );
        return true;
    }

    //Menu options
    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        int id = item.getItemId();
        //Settings
        if(id ==  R.id.action_Settings) {
            Toast.makeText(getApplicationContext(), "You click Settings", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, sett.class);
            startActivity(i);
            checkInternet();
            return true;
        }
        else if (id == R.id.stime){
            Toast.makeText( getApplicationContext(), "You click Sample Time",
                    Toast.LENGTH_SHORT ).show();
            Intent i = new Intent(MainActivity.this, stime.class);
            i.putExtra("sample time",this.mytime);
            startActivityForResult(i,1);
            checkInternet();
            return true;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 1){
            if (data != null){
                Toast.makeText(
                        this,
                        "Your time sample is :  " + data.getStringExtra("Sample Time"),
                        Toast.LENGTH_SHORT).show();
                mytime= data.getStringExtra("Sample Time");
            }
        }
    }


    private void checkInternet(){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else {
            connected = false;
            Toast.makeText(getApplicationContext(),"No Internet Connection\n ",Toast.LENGTH_SHORT ).show();
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        }
    }
}