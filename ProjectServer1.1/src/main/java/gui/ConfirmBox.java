package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;


public class ConfirmBox {

    static boolean answer;

    public static boolean display(String title, String message){
        Stage window = new Stage();

        //blocking other events
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(305);

        Label label = new Label();
        label.setText(message);


        //creating 2 buttons
        Button yesB = new Button("Yes");
        Button noB = new Button("No");

        yesB.setOnAction(e ->{
            answer = true;
            window.close();
        });
        noB.setOnAction(e ->{
            answer = false;
            window.close();
        });


        VBox layout = new VBox(18);
        layout.getChildren().addAll(label,yesB,noB);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        //System.out.println(answer);
        return answer;
    }
}
