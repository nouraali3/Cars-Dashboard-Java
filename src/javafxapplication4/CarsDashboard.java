
package javafxapplication4;

import com.google.gson.Gson;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
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
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import structures.ConsumerThread;
import pojos.KafkaRecord;



public class CarsDashboard extends Application implements MapComponentInitializedListener 
{
    //map
    private static GoogleMapView mapView;
    private static GoogleMap map;
    
    //UI constants
    private static int carReadingsGap = 30;
    private static int readingX = 1030;
    private static int betCarsGap = 50;  
    private static int carsTitleX = 1020;
    
    ObservableList childrenList;
   
    //legend
    Line line;
    Text cars;
    
    //car1 Fields
    Text car1;
    Text velocity1;
    Text fuel1;
    private static Text velocityValue1;
    private static Text fuelValue1;
    
    //car2 fields
    Text car2;
    Text velocity2;
    Text fuel2;
    private static Text velocityValue2;
    private static Text fuelValue2;
    
    //car3 fields
    Text car3;
    Text velocity3;
    Text fuel3;
    private static Text velocityValue3;
    private static Text fuelValue3;
    
    
    @Override
    //called once
    public void start(Stage primaryStage) 
    {
        //1- create root node(children list) and add objects(children) to it
        //2- create children nodes(e.g. line, text, ...) to be displayed cara info , car2 info , car3 info
        //3- create scene and add root to it
        //4- add scene to the stage
        
        
        //1-
        Group root = new Group();
        childrenList = root.getChildren();
        
        //2-
        
        //setting info must come after creating and initializing children list
        //because we are adding items to the children list and it shouldn't be null
        setLegend();
        setMap();

        //create another thread that is responsible for getting records and updating UI
        ConsumerThread consumerThread = new ConsumerThread();
        consumerThread.start();
        
        //3-
        Scene scene = new Scene(root,1200,700);
        
        //4-
        primaryStage.setTitle("Cars Dashboard"); 
        primaryStage.setScene(scene); 
        primaryStage.show();   
    }

    @Override
    public void mapInitialized() {
        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(47.6097, -122.3331))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        map = mapView.createMap(mapOptions);

        //Add a marker to the map
//        MarkerOptions markerOptions = new MarkerOptions();
//
//        markerOptions.position( new LatLong(47.6, -122.3) )
//                    .visible(Boolean.TRUE)
//                    .title("My Marker");
//
//        Marker marker = new Marker( markerOptions );
//        map.addMarker(marker);

    }
    
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    private void setLegend() 
    {
        line = new Line();
        line.setStartX(1000.0);
        line.setEndX(1000.0); 
        line.setStartY(0.0); 
        line.setEndY(700.0);
        childrenList.add(line);
        
        cars = new Text();
        cars.setFont(Font.font("times new roman",FontWeight.BOLD, FontPosture.ITALIC, 30));
        cars.setX(1010);
        cars.setY(30);
        cars.setText("Cars");
        childrenList.add(cars);
        
        
        setCarInfo1();
        setCarInfo2();
        setCarInfo3();
    }
        
    private void setCarInfo1()
    {
        car1 = new Text();
        car1.setFont(Font.font("times new roman",FontWeight.BOLD, FontPosture.ITALIC, 25));
        car1.setX(carsTitleX);
        car1.setY(100);
        car1.setText("Car 1");
        childrenList.add(car1);
        
        velocity1 = new Text();
        velocity1.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        velocity1.setX(readingX);
        velocity1.setY(100+carReadingsGap);
        velocity1.setText("Speed:- ");
        childrenList.add(velocity1);
        
        velocityValue1= new Text("hello");
        velocityValue1.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        velocityValue1.setX(readingX+80);
        velocityValue1.setY(100+carReadingsGap);
        childrenList.add(velocityValue1);
        
        fuel1 = new Text();
        fuel1.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        fuel1.setX(readingX);
        fuel1.setY(100+2*carReadingsGap);
        fuel1.setText("Fuel:- ");
        childrenList.add(fuel1);
        
        fuelValue1 = new Text("hello");
        fuelValue1.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        fuelValue1.setX(readingX+80);
        fuelValue1.setY(100+2*carReadingsGap);
        childrenList.add(fuelValue1);
    }
    
    private void setCarInfo2()
    {
        car2 = new Text();
        car2.setFont(Font.font("times new roman",FontWeight.BOLD, FontPosture.ITALIC, 25));
        car2.setX(carsTitleX);
        car2.setY(250);
        car2.setText("Car 2");
        car2.setFill(Color.LIGHTBLUE);
        childrenList.add(car2);
        
        velocity2 = new Text();
        velocity2.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        velocity2.setX(readingX);
        velocity2.setY(250+carReadingsGap);
        velocity2.setText("Speed:- ");
        velocity2.setFill(Color.LIGHTBLUE);        
        childrenList.add(velocity2);
        
        velocityValue2= new Text("hello");
        velocityValue2.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        velocityValue2.setX(readingX+80);
        velocityValue2.setY(250+carReadingsGap);
        childrenList.add(velocityValue2);
        
        fuel2 = new Text();
        fuel2.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        fuel2.setX(readingX);
        fuel2.setY(250+2*carReadingsGap);
        fuel2.setText("Fuel:- ");
        fuel2.setFill(Color.LIGHTBLUE);
        childrenList.add(fuel2);
        
        fuelValue2= new Text("hello");
        fuelValue2.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        fuelValue2.setX(readingX+80);
        fuelValue2.setY(250+2*carReadingsGap);
        childrenList.add(fuelValue2);
    }
    
    private void setCarInfo3()
    {
        car3 = new Text();
        car3.setFont(Font.font("times new roman",FontWeight.BOLD, FontPosture.ITALIC, 25));
        car3.setX(carsTitleX);
        car3.setY(400);
        car3.setText("Car 3");
        car3.setFill(Color.LIGHTCORAL);
        childrenList.add(car3);
        
        velocity3 = new Text();
        velocity3.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        velocity3.setX(readingX);
        velocity3.setY(400+carReadingsGap);
        velocity3.setText("Speed:- ");
        velocity3.setFill(Color.LIGHTCORAL);
        childrenList.add(velocity3);
        
        velocityValue3= new Text("hello");
        velocityValue3.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        velocityValue3.setX(readingX+80);
        velocityValue3.setY(400+carReadingsGap);
        childrenList.add(velocityValue3);
        
        fuel3 = new Text();
        fuel3.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        fuel3.setX(readingX);
        fuel3.setY(400+2*carReadingsGap);
        fuel3.setText("Fuel:- ");
        fuel3.setFill(Color.LIGHTCORAL);
        childrenList.add(fuel3);
        
        fuelValue3= new Text("hello");
        fuelValue3.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        fuelValue3.setX(readingX+80);
        fuelValue3.setY(400+2*carReadingsGap);
        childrenList.add(fuelValue3);
    }
    
    public void setMap()
    {
        mapView = new GoogleMapView();
        mapView.addMapInializedListener(this);
        childrenList.add(mapView);
    }
    
    
    private final static String TOPIC = "car-data";
    //ip:posrt of the server to which we connect 
    private final static String BOOTSTRAP_SERVERS ="localhost:9092";
    
    private static Consumer<Long, String> createConsumer() 
    {
      final Properties props = new Properties();
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
      props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleConsumer");
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,LongDeserializer.class.getName());
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());

      // Create the consumer using props.
      final Consumer<Long, String> consumer = new KafkaConsumer<>(props);
      // Subscribe to the topic.
      consumer.subscribe(Collections.singletonList(TOPIC));
      return consumer;
    }
    
    
    public static void runConsumer() throws InterruptedException 
    {
        final Consumer<Long, String> consumer = createConsumer();
        final int giveUp = 100;   int noRecordsCount = 0;
        while (true) 
        {
            //poll => fetches the un committed records => wait for new uncommitted records for 5000 ms
            final ConsumerRecords<Long, String> consumerRecords = consumer.poll(5000);
            if (consumerRecords.count()==0) 
            {
                noRecordsCount++;
                if (noRecordsCount > giveUp) 
                    break;
                else 
                    continue;
            }
            
            consumerRecords.forEach(record -> 
            {
                System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",record.key(), record.value(),record.partition(), record.offset());
                KafkaRecord kafkaRecord = getKafkaRecord(record.value());
                System.out.println("KafkaRecord is "+ kafkaRecord);
                
                // let the ui thread(javafx thread) itself not the consumer thread to update map 
                Platform.runLater(new Runnable() 
                {
                    @Override public void run() 
                    {
                        updateUI(kafkaRecord);
                    }
                });
                
            });
            
            // to mark the list of received records before failure  ....so the next poll fetches the un committed records
            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("DONE");
    }
    
    
    private static KafkaRecord getKafkaRecord(String value) 
    {
        Gson g = new Gson();
        KafkaRecord kafkaRecord = g.fromJson(value, KafkaRecord.class);
        return kafkaRecord;
    }
    
    
    private static void updateUI(KafkaRecord kafkaRecord) 
    {
        int tripID = kafkaRecord.getTrip_id();
        //if tripID == trip3
        if(tripID == 3)
        {
            System.out.println("UpdateUI: tripid is 3 ");  
            velocityValue1.setText(Double.toString(kafkaRecord.getVelocity()));
            
            MarkerOptions markerOptions = new MarkerOptions();

            markerOptions.position( new LatLong(kafkaRecord.getLatitude(), kafkaRecord.getLongitude()) )
                        .visible(Boolean.TRUE)
                        .title("Car 1");

            Marker marker = new Marker( markerOptions );

            map.addMarker(marker);
            
            
        }
        //else if tripID == trip4
        else if(tripID == 4)
        {
            System.out.println("UpdateUI: tripid is 4 , lat = "+kafkaRecord.getLatitude()+" , long = "+kafkaRecord.getLongitude());  
            velocityValue2.setText(Double.toString(kafkaRecord.getVelocity()));
            MarkerOptions markerOptions = new MarkerOptions();

            markerOptions.position( new LatLong(kafkaRecord.getLatitude(), kafkaRecord.getLongitude()) )
                        .visible(Boolean.TRUE)
                        .title("Car 2");
            
//            markerOptions.position( new LatLong(47.6, -122.3) )
//                       .visible(Boolean.TRUE)
//                       .title("Car 2");

            Marker marker = new Marker( markerOptions );

            map.addMarker(marker);
            
        }
        //else tripID == trip5
        else if(tripID == 5)
        {
            System.out.println("UpdateUI: tripid is 5 , latitude is "+kafkaRecord.getLatitude()+" , longitude is "+kafkaRecord.getLongitude());  
            velocityValue3.setText(Double.toString(kafkaRecord.getVelocity()));
            MarkerOptions markerOptions = new MarkerOptions();
                
            markerOptions.position( new LatLong(kafkaRecord.getLatitude(), kafkaRecord.getLongitude()) )
                        .visible(Boolean.TRUE)
                        .title("Car 3");

            Marker marker = new Marker( markerOptions );

            map.addMarker(marker);
            
            
        }
        
    }

    
    
}
