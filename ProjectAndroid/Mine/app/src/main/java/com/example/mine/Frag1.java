package com.example.mine;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.nio.charset.StandardCharsets;

public class Frag1 extends Fragment {


    //Initialize var

    TextView tv_location;
    Button b_location;

    protected LocationManager locationManager;
    protected Double latitude, longitude;
    private static final long mindisch = 1;
    private static final long mintim = 1000;

    FusedLocationProviderClient client;
    MqttAndroidClient mqclient;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.frag1, container, false);
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else {
            connected = false;
            Toast.makeText(getActivity(),"No Internet Connection\n",Toast.LENGTH_SHORT ).show();
            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
        }

        //Assign var

        tv_location = (TextView) rootView.findViewById(R.id.gpslocation);

        b_location = (Button) rootView.findViewById(R.id.getlocation);

        client = LocationServices.getFusedLocationProviderClient(getActivity());

        b_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED) {
                    try {
                        getCurrentLocation();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if (requestCode == 100 && grantResults.length>0 && grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            try {
                getCurrentLocation();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(getActivity()
            ,"Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    protected void getCurrentLocation() throws MqttException{
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        try {
                            published(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
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
                                try {
                                    published(String.valueOf(location1.getLatitude()), String.valueOf(location1.getLongitude()));
                                } catch (MqttException e) {
                                    e.printStackTrace();
                                }
                                tv_location.setText("Lat " + String.valueOf(location1.getLatitude()) + "\nLong " + String.valueOf(location1.getLongitude()));
                            }
                        };
                        client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }
            });
        } else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
    public void published( String lat, String lon ) throws MqttException {
        String mylat = null;
        String mylon= null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mylat=lat;
            mylon=lon;
        }

        String topic = "ANDR";
        String test = " test  Lat: " + mylat + "/nLong: " + mylon;

        sett.publishToServer(test);

    }


}
