package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


/*
Class to display Alerts about danger levels and other stuff
 */
public class AlertBox {

    public static void diplayAlert(String title, String message){
        Stage window = new Stage();

        //blocking other events
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(305);

        Label label = new Label();
        label.setText(message);

        Button button = new Button("I understand.");
        button.setOnAction(e -> {
            window.close();
        });

        VBox layout = new VBox(18);
        layout.getChildren().addAll(label,button);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
