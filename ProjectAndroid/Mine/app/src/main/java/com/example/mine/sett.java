package com.example.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class  sett extends AppCompatActivity {
    Button btnConnect, exitbtn;
    EditText ip, port;
    TextView res;

    public static String MQTTHOST;
    public static MqttAndroidClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else {
            connected = false;
            Toast.makeText(getApplicationContext(),"No Internet Connection\n",Toast.LENGTH_SHORT ).show();
            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
        }


        setContentView( R.layout.settings );

        res = (TextView)findViewById( R.id.result );

        ip = (EditText) findViewById( R.id.edittext1 );

        port = (EditText) findViewById( R.id.edittext2 );

        btnConnect  = (Button) findViewById( R.id.button_send );

        exitbtn = (Button) findViewById( R.id.button );

        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getApplicationContext(), "You click Logout ",
                        Toast.LENGTH_SHORT ).show();
                showPopup();
            }
        });

        btnConnect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //save edit text to string
                checkInternet();
                String ipSave = ip.getText().toString();

                //save edit text to string
                String portSave = port.getText().toString();

                //////////////////////////////////////////////////////


                res.setText( "tcp://" + ipSave + ":" + portSave );

                MQTTHOST = "tcp://" + ipSave + ":" + portSave;

                client = new MqttAndroidClient(sett.this,MQTTHOST,"test!!!");

                try {
                    checkInternet();
                    IMqttToken token = client.connect();


                    token.setActionCallback( new IMqttActionListener() {
                        @Override
                        public void onSuccess( IMqttToken asyncActionToken ) {
                            Toast.makeText( sett.this, "connected", Toast.LENGTH_LONG ).show();
//                            String topic = "ANDR";
//                            String payload = "the payload";
//                            byte[] encodedPayload = new byte[0];
//                            try {
//                                encodedPayload = payload.getBytes("UTF-8");
//                                MqttMessage message = new MqttMessage(encodedPayload);
//                                message.setRetained(true);
//                                client.publish(topic, message);
//                            } catch (UnsupportedEncodingException | MqttException e) {
//                                e.printStackTrace();
//                            }
                            String topic1 = "ANDR";
                            MqttCallback myCallback = new MyCallback();
                            client.setCallback(myCallback);
                            int qos = 1;
                            try {

                                IMqttToken subToken = client.subscribe(topic1, qos);
                                subToken.setActionCallback(new IMqttActionListener() {
                                    @Override
                                    public void onSuccess(IMqttToken asyncActionToken) {
                                        // The message was published
                                        Toast.makeText( sett.this, "subscribed to topic", Toast.LENGTH_LONG ).show();
                                    }

                                    @Override
                                    public void onFailure(IMqttToken asyncActionToken,
                                                          Throwable exception) {
                                        // The subscription could not be performed, maybe the user was not
                                        // authorized to subscribe on the specified topic e.g. using wildcards
                                        Toast.makeText( sett.this, "subscribe failure", Toast.LENGTH_LONG ).show();
                                    }
                                });
                            } catch (MqttException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure( IMqttToken asyncActionToken, Throwable exception ) {
                            Toast.makeText( sett.this, "not connected to server", Toast.LENGTH_LONG ).show();
                        }
                    } );
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void disconnectMQTT() throws MqttException{
        System.out.println("Disconnecting MQTT");

        try {
            IMqttToken disconToken = client.disconnect();
            disconToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // we are now successfully disconnected
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // something went wrong, but probably we are disconnected anyway
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static void publishToServer(String text) throws MqttException{

        MqttMessage mes = new MqttMessage(text.getBytes(StandardCharsets.UTF_8));
        mes.setQos(2);
        client.publish("ANDR",mes);
    }

    //Option for logOut
    private void showPopup(){
        AlertDialog.Builder alert = new AlertDialog.Builder( sett.this );
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
        Intent intent= new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT",true);
        startActivity(intent);


        finish();
        System.exit(0);
    }
    public static String setMqtthost(){
        return MQTTHOST;
    }

    private class loginActivity {
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

//    public void dangerhigh(){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        // Setting Alert Dialog Title
//        alertDialogBuilder.setTitle("High Danger close");
//        // Icon Of Alert Dialog
//        alertDialogBuilder.setIcon(R.drawable.ic_baseline_dangerous_24);
//        // Setting Alert Dialog Message
//        alertDialogBuilder.setMessage("Please Keep in mind");
//        alertDialogBuilder.setCancelable(false);
//        alertDialogBuilder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(),"You clicked on Close",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//    }
}
