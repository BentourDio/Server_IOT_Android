import app.app;
import gui.Gui;

import org.eclipse.paho.client.mqttv3.*;
import settings.Settings;
import java.util.*;
import java.io.*;


import java.util.Scanner;

public abstract class MainT implements Runnable {

    public static void main(String[] args) throws MqttException, FileNotFoundException {



        app app = new app();


        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                app.startGUI(args);
                System.out.println("GUI GRACEFULLY SHUT DOWN");
            }
        });
        thread.start();


        //*******************************
        app.connectToDatabase();
        app.initializeDatabase();

        //Create topics, secure connection, create IOT objects with relative info, await pairing
        app.initDevices(Settings.SERVER_IP, Settings.PORT,Settings.IOTNUMBER);

        app.connectToMQTT("tcp");
        //app.startGUI(args);

        //app.print();
        //

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter to close the server ...");
        scanner.nextLine();

        app.disconnectFromMQTT();

        app.disconnectFromDatabase();

        //TESTING GROUND
        // MyDevices Devices = new MyDevices("mpampis",2);

        System.out.println("Bye");

    }

}