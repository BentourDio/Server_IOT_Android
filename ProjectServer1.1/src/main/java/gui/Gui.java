package gui;

import app.app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.geometry.Pos;


import java.io.File;

public class Gui extends Application {
    Stage window;
    private WebEngine webengine;
    private static WebView webview;
    private boolean flagRectangle = false;
    private boolean flagCircle = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //setting window to primaryStage for clarity reasons
        window = primaryStage;
        //setting action for closing of the stage
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });


        //setting the layout for the application
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.TEAL, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setStyle("-fx-border-width: 3; -fx-border-color: black");


        VBox labelBox = new VBox();
        labelBox.setMinWidth(300);

        BorderPane.setAlignment(labelBox, Pos.CENTER);
        BorderPane.setMargin(labelBox, new Insets(100, 12, 12, 12));


        root.setRight(labelBox);


        //separator for labels
        Separator separator1 = new Separator();


        Label iot1 = new Label("iot1");
        Label iot2 = new Label("iot2");
        Label android = new Label("android");
        iot1.setFont(new Font("Arial", 30));
        iot2.setFont(new Font("Arial", 30));
        android.setFont(new Font("Arial", 30));

        Button iotbutton = new Button("IOTs");
        Button iot2button = new Button("IOT2");
        Button andrbutton = new Button("ANDROID");

        //TODO delete this button
        Button test = new Button("lord CREATE THE CIRcLE");

        test.setOnAction(e -> {
            String text = "setDangerCirleIOT(1,iot1)";
            webengine.executeScript("setDangerCirleIOT(3,iot2)");
            webengine.executeScript(text);
        });

        Button stopTimelines = new Button("Stop Timelines");

        //TODO
        Button testing222 = new Button("Destroy Polygon");
        testing222.setOnAction(e ->{
            webengine.executeScript("destroyRectangle()");
        });

        labelBox.getChildren().addAll(iotbutton, new Separator(), andrbutton, new Separator(),stopTimelines,
                new Separator(),test,new Separator(),testing222);

        //add usability to the buttons to fetch data from the items Devices in the app.java class
        //using timeline and keyframe the data on the map can be updated in a 1 second period
        Timeline iotTimeline = iotTimeLoop();
        iotTimeline.setCycleCount(Timeline.INDEFINITE);

        //timeline for android
        Timeline andrTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                app app = new app();

                                String[] myArray = app.fetchAndroidData();
//  0=topic 1=x 2=y

                                setANDRmeasurements(myArray[0],myArray[1],myArray[2],myArray[3]);
                            }
                        })
        );andrTimeline.setCycleCount(Timeline.INDEFINITE);

        iotbutton.setOnAction(e -> iotTimeline.play());
//        iot2button.setOnAction(e -> iot2Timeline.play());
        andrbutton.setOnAction(e -> andrTimeline.play());

        //adding functionality to stop timeline in cases it is needed
        stopTimelines.setOnAction(e -> {
            iotTimeline.stop();
            //iot2Timeline.stop();
            andrTimeline.stop();
        });
//      calling publishServices which calls a js and loads Google Maps api
        publishServices();
        root.setCenter(webview);
        //  finalising dimensions
        window.setTitle("Google Maps Monitor");
        window.setScene(new Scene(root, 1000, 800));
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to visible bounds of the main screen
        window.setX(primaryScreenBounds.getMinX());
        window.setY(primaryScreenBounds.getMinY());
        window.setWidth(primaryScreenBounds.getWidth());
        window.setHeight(primaryScreenBounds.getHeight());
        //setIOTmeasurements(666,666,666,666,666);
        window.show();

    }
    private Timeline iotTimeLoop(){
        return new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                app app = new app();
                                //since this is the iot1 it's index in arraylist is 0
                                String[] myArray0 = app.fetchIOTdata(0);
                                String[] myArray1 = app.fetchIOTdata(1);

//            System.out.println(Arrays.toString(myArray));
        /*
        0=smoke 1=gas 2=temp 3=uv 4=x 5=y
         */                        //index =0 for IOT1 & index = 1 for IOT2


                                setIOTmeasurements(0,myArray0[1], myArray0[0], myArray0[2], myArray0[3], myArray0[4], myArray0[5]);
                                setIOTmeasurements(1,myArray1[1], myArray1[0], myArray1[2], myArray1[3], myArray1[4], myArray1[5]);

                                //checking for danger
                                int danger0, danger1;

                                danger0 = Integer.parseInt(myArray0[6]);
                                danger1 = Integer.parseInt(myArray1[6]);
                                //System.out.println("DANGER0:"+ danger0+" and DANGER1:"+danger1);

                                /*
                                    In the following cases, we check what changes have to be done depending on whether we have danger.
                                    If both IOT devices have danger, all circles are deleted, and their icon is switched with another,
                                    and a rectangle is created in-between them.
                                    If only one or neither of the IOT devices have danger, then we check how many sensors
                                    they have, so that we can put the right colored circle under them.
                                */

                                if (flagCircle){//if circles exist purge them
                                    webengine.executeScript("destroyCircleIOT()");
                                }
                                if(danger0 >0 && danger1 > 0){
                                    //flags prevents core dumb

                                    if (flagRectangle){
                                        webengine.executeScript("destroyRectangle()");
                                    }

                                    if(danger0 == 2){
                                        webengine.executeScript("setIconMediumDanger(iot1)");
                                    }
                                    else{
                                        webengine.executeScript("setIconHighDanger(iot1)");
                                    }

                                    if(danger1 == 2){
                                        webengine.executeScript("setIconMediumDanger(iot2)");
                                    }
                                    else{
                                        webengine.executeScript("setIconHighDanger(iot2)");
                                    }
                                    webengine.executeScript("createRectangle()");
                                    flagRectangle = true;
                                    flagCircle= false;

                                }
                                else {
                                    int[] IOT1Sensors = app.fetchSensors(0);
                                    int[] IOT2Sensors = app.fetchSensors(1);

                                    if (IOT1Sensors[0] > 0 || IOT1Sensors[1] > 0 || IOT1Sensors[2] > 0 || IOT1Sensors[3] > 0) {
                                        webengine.executeScript("setDangerCirleIOT(1,iot1)");
                                    } else {
                                        webengine.executeScript("setDangerCirleIOT(2,iot1)");
                                    }

                                    if (IOT2Sensors[0] > 0 || IOT2Sensors[1] > 0 || IOT2Sensors[2] > 0 || IOT2Sensors[3] > 0) {
                                        webengine.executeScript("setDangerCirleIOT(1,iot2)");
                                    } else {
                                        webengine.executeScript("setDangerCirleIOT(2,iot2)");
                                    }
                                    if (flagRectangle) {
                                        webengine.executeScript("destroyRectangle()");
                                        webengine.executeScript("setIconNoDanger()");
                                    }

                                    flagRectangle = false;
                                    flagCircle = true;
                                }
                            }
                        }));
    }

    public void initGUI(String[] args) {
        launch(args);
    }

    private void closeProgram() {
        boolean answer = ConfirmBox.display("Exiting Program", "Are you sure you want to exit?");
        if (answer) {
            window.close();

            System.out.println("exiting window");
        }
    }

    private void publishServices() {
        try {
            webview = new WebView();
            webview.setVisible(true);
            webengine = webview.getEngine();
            webengine.setJavaScriptEnabled(true);
            File file = new File("src/main/index.html");
            System.out.println(file.exists() + " file existence");
            webengine.load(file.toURI().toURL().toString());

        } catch (Exception ex) {
            System.err.print("error " + ex.getMessage());
            ex.printStackTrace();
        }

    }


    public void setIOTmeasurements(int index,String gas, String smoke, String temp, String uv, String x, String y) {
        String scriptString;

        if (index == 0 ) {
            scriptString = "updateIOT1(" + gas + "," + smoke + "," + temp + "," + uv + "," + x + "," + y + ")";
        }else
            scriptString = "updateIOT2(" + gas + "," + smoke + "," + temp + "," + uv + "," + x + "," + y + ")";

        System.out.println(scriptString);
        webengine.executeScript(scriptString);
    }

    public void setANDRmeasurements(String topic, String x_coord, String y_coord, String battery){
        String scriptString = "updateAndroid("+ x_coord + "," + y_coord + "," + battery + ")";
        System.out.println(scriptString);

        webengine.executeScript(scriptString);
    }
}
