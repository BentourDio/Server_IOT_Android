package com.example.newtabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.nio.charset.StandardCharsets;

public class Option1 extends AppCompatActivity {
    Button btnConnect,publish;
    EditText ip,port;
    TextView res;
    public static String coord_x=null, coord_y=null;
    public static Double x_lat = 0.0, y_long = 0.0, smokeM = 0.0, gasM = 0.0, temperatureM = 0.0, uvM = 0.0;
    public static Boolean coordMode1 , coordMode2;

    static String MQTTHOST ;
    MqttAndroidClient client;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_option1 );

        res = (TextView)findViewById( R.id.result );

        ip = (EditText) findViewById( R.id.edittext1 );

        port = (EditText) findViewById( R.id.edittext2 );

        publish = (Button)findViewById( R.id.publish );


        btnConnect  = (Button) findViewById( R.id.button_send );

        btnConnect.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                //save edit text to string
                String ipSave = ip.getText().toString();

                //save edit text to string
                String portSave = port.getText().toString();

               //////////////////////////////////////////////////////


                res.setText( "tcp://" + ipSave + ":" + portSave );

                MQTTHOST = "tcp://" + ipSave + ":" + portSave;

                client = new MqttAndroidClient(Option1.this,MQTTHOST,"test!!!");

                try {
                    IMqttToken token = client.connect();

                    token.setActionCallback( new IMqttActionListener() {
                        @Override
                        public void onSuccess( IMqttToken asyncActionToken ) {
                            Toast.makeText( Option1.this, "connected", Toast.LENGTH_LONG ).show();
                        }

                        @Override
                        public void onFailure( IMqttToken asyncActionToken, Throwable exception ) {
                            Toast.makeText( Option1.this, "not connected", Toast.LENGTH_LONG ).show();
                        }
                    } );
                } catch (MqttException e) {
                    e.printStackTrace();
                }

            }
        } );

    }
        // for battery value
        public void published( View v ) {
            BatteryManager bm = (BatteryManager) getSystemService( BATTERY_SERVICE );
            String per = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int percentage = bm.getIntProperty( BatteryManager.BATTERY_PROPERTY_CAPACITY );
                per = String.valueOf( percentage );
            }

            // here starts publish
            String topic = "IOT2";
            System.out.println("SAFE STATE");
            String coord_xGps = x_lat.toString();
            System.out.println("this is the error : !!!!!!!!!!"+x_lat);
            String coord_yGps = Double.toString( y_long );
            System.out.println( "Gps coordinates are: " + coord_xGps + coord_yGps );

            String smokeValue = Double.toString( smokeM );
            String gasValue = Double.toString( gasM );
            String tempeValue = Double.toString( temperatureM );
            String uvValue = Double.toString( uvM );

            if ((coordMode1 == true) && (coordMode2 == false)) {
                String message1 = "2-IOT2-smoke-" + smokeValue + "-gas-" + gasValue + "-temp-" + tempeValue + "-UV-" + uvValue + "-x-" + coord_xGps + "-y-" + coord_yGps + "-batt-" + per;

                //String message2 ="Smoke:" + smokeValue + "Gas:" + gasValue + "Temperature:" + tempeValue + "UV: " + uvValue + "Latitude: "  + coord_xGps + "Longitude: " + coord_yGps + " Battery:  " + per;

//                System.out.println( "********Now connecting to ip:" + coord );
                try {
                    client.publish( topic, message1.getBytes( StandardCharsets.UTF_8 ), 2, false );

                } catch (MqttException e) {
                    e.printStackTrace();
                }
//                System.out.println( "WOOOOOORK" + coord );
            } else if ( (coordMode2 == true)) {
                    System.out.println("WE ARE HERE******************************\n");
                    String message2 = "2-IOT2-smoke-" + smokeValue + "-gas-" + gasValue + "-temp-" + tempeValue + "-UV-" + uvValue + "-x-"+coord_x+"-y-"+coord_y + "-batt-" + per;

                   try {
                       client.publish( topic, message2.getBytes( StandardCharsets.UTF_8 ), 2, false );

                   } catch (MqttException e) {
                       e.printStackTrace();
                   }
            }else {
                System.out.println(" coord is null");
            }

        }

        public void setCoordinates(String x, String y){
            coord_x = x; coord_y=y;
        }

        public void setGpsLocation(Double x, Double y){
            x_lat = x;
            y_long = y;
            System.out.println("All went well");
        }

        public void setSmokeMeasurement(Double Smoke){
           smokeM = Smoke;
            System.out.println("Smoke Alert");
        }

    public void setGasMeasurement(Double Gas){
        gasM = Gas;
        System.out.println("Gas Alert");
    }

    public void setTempMeasurement(Double Temperature){
        temperatureM = Temperature;
        System.out.println("Temperature Alert");
    }

    public void setUVMeasurement(Double uV){
        uvM = uV;
        System.out.println("Smoke Alert");
    }

    public void setModeValue(Boolean mode1, Boolean mode2){
        coordMode1 = mode1;
        coordMode2 = mode2;

        System.out.println("Modes are set:" + coordMode1 + coordMode2);

    }

}