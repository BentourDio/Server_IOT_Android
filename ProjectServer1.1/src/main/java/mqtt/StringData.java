package mqtt;

import app.app;
import gui.Gui;
import javafx.application.Platform;
import org.eclipse.paho.client.mqttv3.*;

// Class to split the values of the message sent by the IoT device
public class StringData {
    public int message_id;
    public double smoke_val;
    public double gas_val;
    public double temp_val;
    public double UV_val;

    public double x_coord, y_coord;
    float battery;

    // Get values from given message
    // Message format: device_id-smoke_value-gas_value-temperature_value-UV_value-x-y-battery
    // IOT-smoke-19
    // pairing-jf-

    public void getValuesIoT(String message, String topic) throws MqttException {

        int danger;
        smoke_val=0; gas_val=0; temp_val=-5; UV_val=0;
        String[] split_message = message.split("-");

        if (split_message.length == 1){
            System.out.println("no dash found,incorrect message");
            return;
        }
        app temp_app = new app();

        if (Integer.parseInt(split_message[0]) == 1) {
            //pairing mode/updating mode
            System.out.println("Pairing mode");

            for(int i = 0; i< split_message.length; i ++){
                if (split_message[i].equals("smoke")){
                    smoke_val= Integer.parseInt(split_message[i+1]);
                }
                if (split_message[i].equals("gas")){
                    gas_val= Integer.parseInt(split_message[i+1]);
                }
                if (split_message[i].equals("temp")){
                    temp_val= Integer.parseInt(split_message[i+1]);
                }
                if (split_message[i].equals("UV")){
                    UV_val= Integer.parseInt(split_message[i+1]);
                }
                if (split_message[i].equals("x")){
                    x_coord = Double.parseDouble(split_message[i+1]);
                }
                if (split_message[i].equals("y")){
                    y_coord = Double.parseDouble(split_message[i+1]);
                }
            }

            // Update number of sensors
            temp_app.pairIOT((int)smoke_val, (int)gas_val, (int)temp_val, (int)UV_val, x_coord, y_coord, topic);
        }

        if (Integer.parseInt(split_message[0]) == 2) {
            //measurement sent
            System.out.println("Measurement received");
            for (int i = 1; i < split_message.length; i++) {
                if (split_message[i].equals("smoke")) {
                    if(smoke_val < Double.parseDouble(split_message[i + 1]) && Double.parseDouble(split_message[i + 1]) <= 0.25)
                        smoke_val = Double.parseDouble(split_message[i + 1]);
                }

                if (split_message[i].equals("gas")) {
                    if(gas_val < Double.parseDouble(split_message[i + 1]) && Double.parseDouble(split_message[i + 1]) <= 11)
                        gas_val = Double.parseDouble(split_message[i + 1]);
                }

                if (split_message[i].equals("temp")) {
                    if(temp_val < Double.parseDouble(split_message[i + 1]) && Double.parseDouble(split_message[i + 1]) <= 80)
                        temp_val = Double.parseDouble(split_message[i + 1]);
                }

                if (split_message[i].equals("UV")) {
                    if(UV_val < Double.parseDouble(split_message[i + 1]) && Double.parseDouble(split_message[i + 1]) <= 11)
                        UV_val = Double.parseDouble(split_message[i + 1]);
                }

                if (split_message[i].equals("x")) {
                    x_coord = Double.parseDouble(split_message[i + 1]);
                }

                if (split_message[i].equals("y")) {
                    y_coord = Double.parseDouble(split_message[i + 1]);
                }

                if (split_message[i].equals("batt")) {
                    battery = Integer.parseInt(split_message[i + 1]);
                }
            }

            // Check if there was danger
            danger = temp_app.findDanger(smoke_val, gas_val, temp_val, UV_val);

            // Update the values of the IOT
            temp_app.updateIOT(smoke_val, gas_val, temp_val, UV_val, x_coord, y_coord, battery, danger, topic);

            // If there was danger, inform the android device and update the database
            if(danger != 0){
                temp_app.sendMessageToAndroid(x_coord, y_coord,danger, topic);
                temp_app.updateDatabase(danger, x_coord, y_coord, smoke_val, gas_val, temp_val, UV_val);
            }
        }
    }

    public void getValuesAndroid(String message) {
        String[] split_message = message.split("-");

        if (!(split_message[0].equals("3")) ) {//messages starting with code 3 are ignored because servers sends them
            if (split_message.length == 1) {
                System.out.println("no dash found,incorrect message");
                return;
            }
            app temp_app = new app();

            if (Integer.parseInt(split_message[0]) == 1 || Integer.parseInt(split_message[0]) == 2) {
                //pairing/updating mode
//            System.out.println("Pairing mode");
                for (int i = 1; i < split_message.length; i++) {
                    if (split_message[i].equals("x")) {
                        x_coord = Double.parseDouble(split_message[i + 1]);
                    }
                    if (split_message[i].equals("y")) {
                        y_coord = Double.parseDouble(split_message[i + 1]);
                    }
                    if (split_message[i].equals("batt")) {

                        battery = Integer.parseInt(split_message[i + 1]);

                    }
                }
                temp_app.updateAndroid(x_coord, y_coord, battery);
            }
        }
    }
}
