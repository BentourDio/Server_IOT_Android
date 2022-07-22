package com.example.newtabs;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Gps extends AppCompatActivity implements IBaseGpsListener  {

    private static final int PERMISSION_LOCATION = 1000;


    //Initialize var
    FusedLocationProviderClient client;
    public double lat;
    public double lonG;
    TextView tv_location;
    Button b_location;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_gps );

        //Assign var

        tv_location = findViewById( R.id.gpslocation );

        b_location = findViewById( R.id.getlocation );


        client = LocationServices.getFusedLocationProviderClient(this);

        b_location.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                //check for location permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION )
                                != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions( new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION );
                } else {
                    showLocation();
                }
            }
        } );
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if (requestCode == PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showLocation();
            } else {
                Toast.makeText( this, "Permission not granted!", Toast.LENGTH_SHORT ).show();
                finish();
            }
        }
    }
    @SuppressLint("MissingPermission")
    private void showLocation() {
        LocationManager locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        // check if gps enabled
        if (locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER )||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //start Locating
            tv_location.setText( "Loading location..." );
//            if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
//
                        Option1 temp2 = new Option1();
                        lat = location.getLatitude();
                        lonG =location.getLongitude();

                        temp2.setGpsLocation( lat,lonG );
                        tv_location.setText("Lat " + String.valueOf(location.getLatitude()) + "\nLong " + String.valueOf(location.getLongitude()));
                    } else {
                        com.google.android.gms.location.LocationRequest locationRequest = new com.google.android.gms.location.LocationRequest()
                                .setPriority(LocationRequest.QUALITY_HIGH_ACCURACY)
                                .setInterval(1000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
//
                                Option1 temp2 = new Option1();
                                lat = location.getLatitude();
                                lonG =location.getLongitude();

                                temp2.setGpsLocation( lat,lonG );
                                tv_location.setText("Lat " + String.valueOf(location1.getLatitude()) + "\nLong " + String.valueOf(location1.getLongitude()));
                            }
                        };
                        client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }
            });
        } else{
            //enable gps
            Toast.makeText( this,"Enable GPS!",Toast.LENGTH_SHORT ).show();
            startActivity(new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS) );
        }
    }

    //show Location as string
    private String hereLocation(Location location){

        Option1 temp2 = new Option1();
        lat = location.getLatitude();
        lonG =location.getLongitude();

        temp2.setGpsLocation( lat,lonG );

        return "Lat: " + location.getLatitude() + "\nLon: " + location.getLongitude();
    }

    @Override
    public void onLocationChanged( Location location ) {
        //update Location
        tv_location.setText( hereLocation(location) ) ;
    }

    @Override
    public void onProviderDisabled( String provider ) {
        //empty
    }

    @Override
    public void onProviderEnabled( String provider ) {
        //empty
    }

    @Override
    public void onStatusChanged( String provider, int status, Bundle extras ) {
        //empty

    }

    @Override
    public void onGpsStatusChanged( int event ) {

    }



}