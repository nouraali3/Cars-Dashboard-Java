/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 *
 * @author user
 */
public class CarsDashboard extends Application 
{
    private static int carReadingsGap = 30;
    private static int carReadingsX = 1030;
    private static int betCarsGap = 50;  
    private static int carsTitleX = 1020;
    
    
    @Override
    //called once
    public void start(Stage primaryStage) 
    {
        //1- create child nodes(e.g. line, text, ...) to be displayed
        //2- create root node and add objects to it(children list)
        //3- create scene and add root to it
        //4- add scene to the stage
        
        
        //1-
        Line line = new Line();
        line.setStartX(1000.0);
        line.setEndX(1000.0); 
        line.setStartY(0.0); 
        line.setEndY(700.0);
        
        
        Text cars = new Text();
        cars.setFont(Font.font("times new roman",FontWeight.BOLD, FontPosture.ITALIC, 30));
        cars.setX(1010);
        cars.setY(30);
        cars.setText("Cars");
        
        
        Text car1 = new Text();
        car1.setFont(Font.font("times new roman",FontWeight.BOLD, FontPosture.ITALIC, 25));
        car1.setX(carsTitleX);
        car1.setY(100);
        car1.setText("Car 1");
        
        Text carVelocity1 = new Text();
        carVelocity1.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        carVelocity1.setX(carReadingsX);
        carVelocity1.setY(100+carReadingsGap);
        carVelocity1.setText("Speed:- ");
        
        Text carFuel1 = new Text();
        carFuel1.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        carFuel1.setX(carReadingsX);
        carFuel1.setY(100+2*carReadingsGap);
        carFuel1.setText("Fuel:- ");
        
        
        Text car2 = new Text();
        car2.setFont(Font.font("times new roman",FontWeight.BOLD, FontPosture.ITALIC, 25));
        car2.setX(carsTitleX);
        car2.setY(250);
        car2.setText("Car 1");
        car2.setFill(Color.LIGHTBLUE);
        
        Text carVelocity2 = new Text();
        carVelocity2.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        carVelocity2.setX(carReadingsX);
        carVelocity2.setY(250+carReadingsGap);
        carVelocity2.setText("Speed:- ");
        carVelocity2.setFill(Color.LIGHTBLUE);
        
        Text carFuel2 = new Text();
        carFuel2.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        carFuel2.setX(carReadingsX);
        carFuel2.setY(250+2*carReadingsGap);
        carFuel2.setText("Fuel:- ");
        carFuel2.setFill(Color.LIGHTBLUE);
        
        
        
        Text car3 = new Text();
        car3.setFont(Font.font("times new roman",FontWeight.BOLD, FontPosture.ITALIC, 25));
        car3.setX(carsTitleX);
        car3.setY(400);
        car3.setText("Car 1");
        car3.setFill(Color.LIGHTCORAL);
        
        Text carVelocity3 = new Text();
        carVelocity3.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        carVelocity3.setX(carReadingsX);
        carVelocity3.setY(400+carReadingsGap);
        carVelocity3.setText("Speed:- ");
        carVelocity3.setFill(Color.LIGHTCORAL);
        
        Text carFuel3 = new Text();
        carFuel3.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        carFuel3.setX(carReadingsX);
        carFuel3.setY(400+2*carReadingsGap);
        carFuel3.setText("Fuel:- ");
        carFuel3.setFill(Color.LIGHTCORAL);
        
        
        
        //2-
        Group root = new Group();
        ObservableList childrenList = root.getChildren();
        childrenList.add(line);
        childrenList.add(cars);
        childrenList.add(car1);
        childrenList.add(carVelocity1);
        childrenList.add(carFuel1);
        childrenList.add(car2);
        childrenList.add(carVelocity2);
        childrenList.add(carFuel2);
        childrenList.add(car3);
        childrenList.add(carVelocity3);
        childrenList.add(carFuel3);
        
        //3-
        Scene scene = new Scene(root,1200,700);
        
        //4-
        primaryStage.setTitle("Sample application"); 
        primaryStage.setScene(scene); 
        primaryStage.show();   
    }

    
    public static void main(String[] args) {
        launch(args);
        
    }
}
