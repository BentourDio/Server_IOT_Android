package com.example.mine;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.util.*;
import java.io.*;

public class MyCallback implements MqttCallback {
    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Message arrived from topic:"+topic + " message= "+message);
    //message has form 3-distance(double)-dangerlevel(medium||high)
        String mess =  message.toString();
        System.out.println("the mess is "+mess);
        String[] split_message = mess.split("-");

        if (split_message.length == 0){
            System.out.println("no dash found,incorrect message");
            return;
        }
        //split_message[1] einai to distance se double se metra
        if(Integer.parseInt(split_message[0]) == 3){
            System.out.println("I am doing stuff mom");
            if (split_message[2].equals("high")){
                //then we have high danger, act accordingly
                System.out.println("Danger:"+split_message[2]);
              //  final MediaPlayer mp= MediaPlayer.create( R.raw.sample);
              //  mp.start();

            }
            if (split_message[2].equals("medium")){
                //then medium danger
                System.out.println("Danger:"+split_message[2]);
            }
        }


    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
