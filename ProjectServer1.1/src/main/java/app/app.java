package app;

import com.google.common.util.concurrent.Monitor;
import database.DatabaseManager;
import gui.Gui;
import devices.Android;
import devices.IOT;
import javafx.application.Application;
import math.GeoDistance;
import mqtt.MyCallback;
import mqtt.MyDevices;
import org.eclipse.paho.client.mqttv3.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class app {


    private static final String COMMA_DELIMITER = ",";
    public static DatabaseManager db = new DatabaseManager();
    public static MyDevices Devices;
    public static MqttAsyncClient myClient;
    public Gui gui = new Gui();

//    private Monitor mutexIOT = new Monitor();
    private  final ReentrantLock mutexIOT = new ReentrantLock();
    private  final ReentrantLock mutexAND = new ReentrantLock();


////////////////////////////////////////////////////
    public app() {
//        db.initialize();
    }
////////////////////////////////////////////////////

    // Methods for the database
    public void connectToDatabase(){
        // only connect to database
        db.connect();

        System.out.println("SQL database connection established\n");
    }

    public void disconnectFromDatabase(){
        db.danger_search("High Danger");
        db.disconnect();
    }

    public void initializeDatabase(){
        db.initialize();
    }

    public void updateDatabase(int case_num, double x, double y, double smoke, double gas, double temp, double UV){
        db.update(case_num, x, y, smoke, gas, temp, UV);
    }

    public void date_searchDatabase(String start_date, String end_date){
        db.date_search(start_date, end_date);
    }

    public void danger_searchDatabase(String danger){
        db.danger_search(danger);
    }

    public void deleteFromDatabase(String type, double value){
        db.delete(type, value);
    }

    //these methods are used for pairing/updating the sensors, not their measurementsM

    // Methods to connect and disconnect to the mqtt
    public void disconnectFromMQTT() throws MqttException {
        System.out.println("Disconnecting\n");


        IMqttToken tokenClient = myClient.disconnect();
        tokenClient.waitForCompletion();

        myClient.close();
    }

    public void connectToMQTT(String ip) throws MqttException {
        //creating 3 ASYNC mqtt topics

        myClient = new MqttAsyncClient("tcp://127.0.0.1:1883",UUID.randomUUID().toString());

        MyCallback myClientCallback = new MyCallback();

        myClient.setCallback(myClientCallback);

        IMqttToken tokenClient = myClient.connect();
        tokenClient.waitForCompletion();

        for(int i =0; i < Devices.counterIOT; i++) {
            IOT temp = Devices.listIOT.get(i);
            System.out.println("connect IOT"+temp.topic);

            myClient.subscribe(temp.topic,2);
        }
        //////////////////////////////////////////////////////////////////////
//        myClient.subscribe(Devices.IOT1.topic,2);
//        myClient.subscribe(Devices.IOT2.topic,2);
        myClient.subscribe(Devices.ANDR.Topic,2);
//        myClient.pub

        System.out.println("3 separate asynchronous topics have been created "+ip);
//        System.out.println(Devices.IOT1.Topic+Devices.IOT2.Topic+Devices.Andr.Topic);


    }

    // Checks if there is danger
    // It returns the case of the danger
    public int findDanger(double smoke_val, double gas_val, double temp_val, double UV_val){
        // Find if there is danger
        // And which case has occurred
        System.out.println("Entered FindDanger");
        if(smoke_val > 0.14 && gas_val > 9.15 && temp_val > 50.0 && UV_val > 6.0){
            // Case 4: all sensors are at dangerous levels
            return 4;
        }
        else if(smoke_val > 0.14 && gas_val > 9.15){
            // Case 1: smoke and gas are at dangerous levels
            return 1;
        }
        else if(gas_val > 9.15){
            // Case 3: only gas sensor is at dangerous levels
            return 3;
        }
        else if(temp_val > 50.0 && UV_val > 6.0){
            // Case 2: temp and UV are at dangerous levels
            return 2;
        }
        else{
            // There is no danger
            return 0;
        }
    }

    /////////////////////////////////////////////////////////
    public void initDevices(String IP, int port,int n){
        Devices = new MyDevices("tcp://127.0.0.1",n);


        /*Devices.IOT1 = new IOT("IOT1",0,0,0,0);
        Devices.IOT2 = new IOT("IOT2",0,0,0,0);
        Devices.ANDR = new Android("ANDR",0,0,0);*/

    }

    ///////////////////////////////////////////////////////
    public void Pairing(){

    }

    public void print(){
        //Devices.printStuff(Devices);
        Devices.output(Devices);

    }


    public void setAndroidX_coord(double x){
        Devices.ANDR.x_coord = x;
    }

    public void setAndroidY_coord(double y){
        Devices.ANDR.y_coord = y;
    }

    public void setAndroidBattery(float b){
        Devices.ANDR.battery = b;
    }

    public void testing(){



        System.out.println("Bye.");
    }



    public void pairIOT(int smoke, int gas, int temp, int UV, double x, double y, String topic){
        //to find which cell in arraylist is the IOT
        //System.out.println("Entering Pairing");
        //////////////////////////////////////////////////////////////
        int index = Devices.getIndexTopic(topic,Devices);

        try {
            mutexIOT.lock();
            Devices.listIOT.get(index).smoke_sensor = smoke;
            Devices.listIOT.get(index).gas_sensor = gas;
            Devices.listIOT.get(index).temp_sensor = temp;
            Devices.listIOT.get(index).UV_sensor = UV;

            Devices.listIOT.get(index).data_x_coord = x;
            Devices.listIOT.get(index).data_y_coord = y;
        }catch (Exception e) {
            System.out.println("synchronization IOT went wrong");
        }
        finally{
            mutexIOT.unlock();
        }
    }

    public void updateIOT(double smoke, double gas, double temp, double UV, double x, double y, float battery, int danger, String topic){
        int index = Devices.getIndexTopic(topic,Devices);
        try {
            mutexIOT.lock();
            Devices.listIOT.get(index).latest_data_smoke = smoke;
            Devices.listIOT.get(index).latest_data_gas = gas;
            Devices.listIOT.get(index).latest_data_temp = temp;
            Devices.listIOT.get(index).latest_data_UV = UV;
            Devices.listIOT.get(index).latest_danger = danger;
            Devices.listIOT.get(index).data_x_coord = x;
            Devices.listIOT.get(index).data_y_coord = y;
            Devices.listIOT.get(index).battery = battery;
        }catch (Exception e) {
            System.out.println("synchronization IOT went wrong");
        }
        finally{
            mutexIOT.unlock();
        }
    }


    public void updateAndroid(double x, double y, float battery){
        try {
            mutexAND.lock();
            Devices.ANDR.battery = battery;
            Devices.ANDR.x_coord = x;
            Devices.ANDR.y_coord = y;
        }catch (Exception e){
            System.out.println("smth went wrong syncro andr");
        }finally {
            mutexAND.unlock();
        }
    }

    public void startGUI(String[] args) {
        gui.initGUI(args);
        //gui.start();
    }

    public String[] fetchIOTdata(int index) {
        String[] myArray = new String[7];

        try {
            mutexIOT.lock();
            myArray[0] = "" + Devices.listIOT.get(index).latest_data_smoke;
            myArray[1] = "" + Devices.listIOT.get(index).latest_data_gas;
            myArray[2] = "" + Devices.listIOT.get(index).latest_data_temp;
            myArray[3] = "" + Devices.listIOT.get(index).latest_data_UV;
            myArray[4] = "" + Devices.listIOT.get(index).data_x_coord;
            myArray[5] = "" + Devices.listIOT.get(index).data_y_coord;
            myArray[6] = "" + Devices.listIOT.get(index).latest_danger;
        } catch(Exception e){
            System.out.println("error fetchIOTdata mutex");
        }finally {
            mutexIOT.unlock();
        }
        return myArray;
    }

    public int[] fetchSensors(int index) {
        int[] myArray = new int[4];

        try {
            mutexIOT.lock();
            myArray[0] = Devices.listIOT.get(index).gas_sensor;
            myArray[1] = Devices.listIOT.get(index).smoke_sensor;
            myArray[2] = Devices.listIOT.get(index).temp_sensor;
            myArray[3] = Devices.listIOT.get(index).UV_sensor;
        } catch(Exception e){
            System.out.println("error fetchSensors mutex");
        }finally {
            mutexIOT.unlock();
        }
        return myArray;
    }

    public String[] fetchAndroidData(){
        String[] myArray = new String[4];

        try{
            mutexAND.lock();
            myArray[0] = Devices.ANDR.Topic;
            myArray[1] = ""+Devices.ANDR.x_coord;
            myArray[2] = ""+Devices.ANDR.y_coord;
            myArray[3] = ""+Devices.ANDR.battery;
        }catch (Exception e){
            System.out.println("error fetchAndroidData mutex");
        }finally {
            mutexAND.unlock();
        }
        return myArray;
    }

    public void sendMessageToAndroid(double x_coord, double y_coord, int danger, String topic) throws MqttException {
        double d;
        double x_andr, y_andr;

        // To get x, y coordinates of android
        String[] andr_data = this.fetchAndroidData();

        x_andr = Double.parseDouble(andr_data[1]);
        y_andr = Double.parseDouble(andr_data[2]);

        // To see if the other IOT had received values with dangerous levels
        int previous_danger;

        int index = Devices.getIndexTopic(topic,Devices);

        // Index for the other IOT device
        int index2;

        if(index == 0) {
            index2 = 1;
        }
        else{
            index2 = 0;
        }

        previous_danger = Devices.listIOT.get(index2).latest_danger;

        // If both IOT devices have received values with dangerous levels
        if(previous_danger  > 0){
            // Calculate distance between android and the middle of the IOT devices
            double x2, y2;

            x2 = Devices.listIOT.get(index2).data_x_coord;
            y2 = Devices.listIOT.get(index2).data_y_coord;

            d = GeoDistance.distance((x_coord + x2) / 2, (y_coord + y2) / 2, x_andr, y_andr, "K") * 1000;
        }
        else{
            // Else calculate distance between android and the IOT device that currently has values with dangerous levels
            d = GeoDistance.distance(x_coord, y_coord, x_andr, y_andr, "K") * 1000;
        }

        // Send message to android
        String mes;
        if(danger==2) {
             mes = "3-" + d+"-medium";
        }
        else{
             mes = "3-" + d + "-high";
        }

        MqttMessage message = new MqttMessage(mes.getBytes());
        message.setQos(2);
        myClient.publish(andr_data[0], message);
    }

    public void readCSV() throws FileNotFoundException {
        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("/Users/dionysiosbentour/Desktop/csv2.csv"));) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        }
        String test;
        for(int i = 0; i<records.size(); i++) {
            test = (records.get(i).toString()).replace("[","");
            test = test.replace("]","");
            String [] curr = test.split("\t");
            if(curr[0]!= null && curr[1] != null)
                System.out.println(curr[0].toString()+"\t"+curr[1].toString());
        }
    }

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

}
