package com.example.mine;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Frag2 extends Fragment {

    public static BufferedReader reader1;
    public static BufferedReader reader2;
    String [] curr;
    String line1;
    int mitime, i=0;
    MqttAndroidClient mqclient;
    //Initialize var

    TextView tv_location;
    Button b_location;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.frag2, container, false);
        checkInternet();



        tv_location =  (TextView) rootView.findViewById( R.id.manlocation );

        b_location =  (Button) rootView.findViewById( R.id.getmanlocation );

        b_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream inputStream1 = getResources().openRawResource(R.raw.user_location_1);
                InputStream inputStream2 = getResources().openRawResource(R.raw.user_location_2);
                int random =new Random().nextInt(2);
                if( MainActivity.mytime != null) {
                    mitime = Integer.valueOf(MainActivity.mytime);
                }
                switch (random){
                    case 0:
                        i=0;
                        reader1 = new BufferedReader(new
                                InputStreamReader(inputStream1, Charset.forName("UTF-8")));
                        if( MainActivity.mytime == null ){
                            try {
                                line1 = reader1.readLine();
                                try {
                                    reader1.mark(0);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                while (line1 != null){
                                    curr = line1.split("\t");
                                    if(curr[0]!= null && curr[1] != null){
                                        System.out.println(curr[0]+"\t"+curr[1]);
                                        checkInternet();
                                        published(curr[0],curr[1]);
                                    }
                                    line1 = reader1.readLine();
                                }
                            } catch (IOException | MqttException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            try{
                                line1 = reader1.readLine();
                                i=0;
                                while ((line1 != null) && (i< mitime)){
                                    curr = line1.split("\t");
                                    if(curr[0]!= null && curr[1] != null){
                                        System.out.println(curr[0]+"\t"+curr[1]);
                                        checkInternet();
                                        published(curr[0],curr[1]);
                                    }
                                    line1 = reader1.readLine();
                                    i++;
                                }
                            } catch (IOException | MqttException e){
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 1:
                        i=0;
                        reader2 = new BufferedReader(new
                                InputStreamReader(inputStream2, Charset.forName("UTF-8")));
                        if( MainActivity.mytime == null ){
                            try {
                                line1 = reader2.readLine();
                                while (line1 != null ){
                                    curr = line1.split("\t");
                                    if(curr[0]!= null && curr[1] != null){
                                        System.out.println(curr[0]+"\t"+curr[1]);
                                        checkInternet();
                                        published(curr[0],curr[1]);
                                    }
                                    line1 = reader2.readLine();
                                }
                            } catch (IOException | MqttException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            try{
                                line1 = reader2.readLine();
                                i=0;
                                while ((line1 != null) && (i< mitime)) {
                                    curr = line1.split("\t");
//                                    System.out.println("Check passed user 1\n");
                                    if(curr[0]!= null && curr[1] != null){
                                        System.out.println(curr[0]+"\t"+curr[1]);
                                        checkInternet();
                                        published(curr[0],curr[1]);
                                    }
                                    line1 = reader2.readLine();
                                    i++;
                                }
                            } catch (IOException | MqttException e){
                                e.printStackTrace();
                            }
                        }
                        break;
                    default:
                        System.out.println("HERE WE ARE \n");
                        break;
                }
                //close input
                if (inputStream1 != null) {
                    try {
                        inputStream1.close();
                    }
                    catch(IOException e)
                    {
                        //Very bad things just happened... handle it
                        e.printStackTrace();
                    }
                }
                //close input
                if (inputStream2 != null) {
                    try {
                        inputStream2.close();
                    }
                    catch(IOException e) {
                        //Very bad things just happened... handle it
                        e.printStackTrace();
                    }
                }
            }
        });
        return rootView;
    }

    private void checkInternet(){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else {
            connected = false;
            Toast.makeText(getActivity(),"No Internet Connection\n ",Toast.LENGTH_SHORT ).show();
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        }
    }

    public void published( String lat, String lon ) throws MqttException {
        String mylat = null;
        String mylon= null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //int percentage = bm.getIntProperty( BatteryManager.BATTERY_PROPERTY_CAPACITY );
            mylat=lat;
            mylon=lon;
        }

        String topic = "ANDR";
        String test = " test  Lat: " + mylat + "/nLong: " + mylon;
//        if (getActivity() != null) {
//            ((sett) getActivity()).publishToServer(test);
//        }
        sett.publishToServer(test);
//        try {
//            mqclient.publish( topic, test.getBytes( StandardCharsets.UTF_8 ), 0, false );
//
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }

    }
}

//                                        |
//  I N S I D E WHILE --------------------\/
//                                    curr = line1.split("\t");
////                                    System.out.println("Check passed user 1\n");
//                                            if(curr[0]!= null && curr[1] != null){
//                                            System.out.println(curr[0]+"\t"+curr[1]);
//                                            if (getActivity() != null) {
//                                            ((sett) getActivity()).publishToServer(curr[0]+"\t"+curr[1]);
//                                            }
//                                            }
//                                            line1 = reader1.readLine();
//                                            i++;



//while ((line1 != null) && (i<= mitime)){
//        curr = line1.split("\t");
////                                    System.out.println("Check passed user 1\n");
//        if(curr[0]!= null && curr[1] != null){
//        System.out.println(curr[0]+"\t"+curr[1]);
////                                        if (getActivity() != null) {
////                                            ((sett) getActivity()).publishToServer(curr[0]+"\t"+curr[1]);
////                                        }
//        }
//
//        line1 = reader2.readLine();
//        i++;
//        }