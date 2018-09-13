/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JLabel;

/**
 *
 * @author user
 */
public class JavaFXApplication4 extends Application 
{
    private List records ;
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        
        
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        setViews(root);
        
        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
        
    }
    
    private static void setViews(StackPane root) 
    {
        long curTime = System.currentTimeMillis();
        Label jLabel4 = new Label();
        jLabel4.setText(Long.toString(curTime));
        System.out.println(jLabel4.getAlignment());
        jLabel4.setAlignment(Pos.BASELINE_CENTER);
        jLabel4.setRotate(70);
        root.getChildren().add(jLabel4);
        
    }
}
