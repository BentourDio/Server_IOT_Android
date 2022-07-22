package com.example.newtabs;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.newtabs.databinding.Fragment1Binding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newtabs.ui.main.SectionsPagerAdapter;
import com.example.newtabs.databinding.ActivityMainBinding;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity implements Fragment1.OnFragmentInteractionListener,Fragment2.OnFragmentInteractionListener,Fragment3.OnFragmentInteractionListener {

    public static TabLayout mTablayout;
    public Boolean automaTic = false, manUal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


        //toolbar for the dropDown Menu
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );




        mTablayout = (TabLayout) findViewById( R.id.tablayout );
        mTablayout.addTab( mTablayout.newTab().setText( "Smoke Sensor" ) );
        mTablayout.addTab( mTablayout.newTab().setText( "Gas Sensor" ) );
        mTablayout.addTab( mTablayout.newTab().setText( "Temperature Sensor" ) );
        mTablayout.addTab( mTablayout.newTab().setText( "UV detection Sensor" ) );
        mTablayout.setTabGravity( TabLayout.GRAVITY_FILL );

        final ViewPager viewPager = (ViewPager) findViewById( R.id.pager );
        final com.example.newtabs.PagerAdapter adapter = new com.example.newtabs.PagerAdapter( getSupportFragmentManager(), mTablayout.getTabCount() );
        viewPager.setAdapter( adapter );
        viewPager.setOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( mTablayout ) );

        mTablayout.setOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
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



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate( R.menu.main_menu,menu );
        return true;

    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        int id = item.getItemId();

        if (id == R.id.action_Settings) {
            Toast.makeText( getApplicationContext(), "You click Settings",
                    Toast.LENGTH_SHORT ).show();
            Intent i = new Intent( MainActivity.this,Option1.class);
            startActivity(i);
            return true;
        } else if (id == R.id.action_SendPosition) {
            Toast.makeText( getApplicationContext(), "You click Send Position",
                    Toast.LENGTH_SHORT ).show();



        }else if(id == R.id.subitem1) {

            Option1 tempBool = new Option1();

            automaTic = true;
            manUal = false;

            tempBool.setModeValue(automaTic,manUal  );

            Toast.makeText( getApplicationContext(), "Automatic",
                    Toast.LENGTH_SHORT ).show();
            Intent i = new Intent(MainActivity.this,Gps.class);
            startActivity( i );
            return true;


        }else if(id == R.id.subitem2) {

            Option1 tempBool = new Option1();

            automaTic = false;
            manUal = true;

            tempBool.setModeValue( automaTic,manUal );

            Toast.makeText( getApplicationContext(), "Manual",
                    Toast.LENGTH_SHORT ).show();
            Intent i = new Intent( MainActivity.this,Option2.class);
            startActivity(i);
            return true;


        }else if (id == R.id.action_AddSensor) {
            Toast.makeText( getApplicationContext(), "You click Αdd Sensor",
                    Toast.LENGTH_SHORT ).show();
            Intent i = new Intent( MainActivity.this,Option3.class);
            startActivity(i);
            return true;

        } else if (id == R.id.action_Logout) {
            Toast.makeText( getApplicationContext(), "You click Logout ",
                    Toast.LENGTH_SHORT ).show();

            showPopup();
            return true;
        }
        return true;
    }
    //Option for logOut
    private void showPopup(){
        AlertDialog.Builder alert = new AlertDialog.Builder( MainActivity.this );
        alert.setMessage( "Are you sure you want to exit" )
                .setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        logout();
                    }
                } ).setNegativeButton( "No", null );

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    private void logout(){
        startActivity(new Intent(this, loginActivity.class));
        finish();
    }




    private class loginActivity {
    }

}