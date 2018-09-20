
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
    private static int betCarsGap = 50;
    private static int legendX = 800;
    private static int carsTitleX = legendX+10;
    private static int readingX = carsTitleX+5;
    
    
    //icons paths
    private String carIconPath1 = "icons8-fiat-500-60.png";
    private String carIconPath2 = "icons8-sedan-40.png";
    private String carIconPath3 = "icons8-sedan-64.png";
            
    ObservableList childrenList;
   
    //legend
    Line line;
    Line line2;
    Text cars;
    
    //car1 Fields
    Text car1;
    Text speed1;
    Text rpm1;
    Text fuel1;
    private static Text speedValue1;
    private static Text rpmValue1;
    private static Text fuelValue1;
    
    
    //car2 fields
    Text car2;
    Text speed2;
    Text rpm2;
    Text fuel2;
    private static Text speedValue2;
    private static Text rpmValue2;
    private static Text fuelValue2;
    
    
    //car3 fields
    Text car3;
    Text speed3;
    Text rpm3;
    Text fuel3;
    private static Text speedValue3;
    private static Text rpmValue3;
    private static Text fuelValue3;
    
    public static void main(String[] args) 
    {
        launch(args);
    }
    
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
        Scene scene = new Scene(root,1050,600);
        
        //4-
        primaryStage.setTitle("Cars Dashboard"); 
        primaryStage.setScene(scene); 
        primaryStage.show();   
    }

    public void setMap()
    {
        mapView = new GoogleMapView();
        mapView.addMapInializedListener(this);
        childrenList.add(mapView);
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
                .zoom(4);

        map = mapView.createMap(mapOptions);
        
        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position( new LatLong(43,21) ).icon(carIconPath1).visible(Boolean.TRUE).title("Car 1");
        markerOptions.position( new LatLong(47,-122) ).visible(Boolean.TRUE).title("Car 1");
        Marker marker = new Marker( markerOptions );
        map.addMarker(marker);
        
    }
    
    
    
    private void setLegend() 
    {
        line = new Line();
        line.setStartX(legendX+1);
        line.setEndX(legendX+1); 
        line.setStartY(0.0); 
        line.setEndY(600.0);
        childrenList.add(line);
        
        line2 = new Line();
        line2.setStartX(legendX+1);
        line2.setEndX(1050); 
        line2.setStartY(35); 
        line2.setEndY(35);
        childrenList.add(line2);
        
        cars = new Text();
        cars.setFont(Font.font("times new roman",FontWeight.BOLD, FontPosture.ITALIC, 30));
        cars.setX(legendX+5);
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
        car1.setFont(Font.font("times new roman", FontPosture.ITALIC, 25));
        car1.setX(carsTitleX);
        car1.setY(100);
        car1.setText("Car 1");
        childrenList.add(car1);
        
        speed1 = new Text();
        speed1.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        speed1.setX(readingX);
        speed1.setY(100+carReadingsGap);
        speed1.setText("Speed:- ");
        childrenList.add(speed1);
        
        speedValue1= new Text("not available");
        speedValue1.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        speedValue1.setX(readingX+80);
        speedValue1.setY(100+carReadingsGap);
        childrenList.add(speedValue1);
        
        rpm1 = new Text();
        rpm1.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        rpm1.setX(readingX);
        rpm1.setY(100+2*carReadingsGap);
        rpm1.setText("RPM:- ");
        childrenList.add(rpm1);
        
        rpmValue1 = new Text("not available");
        rpmValue1.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        rpmValue1.setX(readingX+80);
        rpmValue1.setY(100+2*carReadingsGap);
        childrenList.add(rpmValue1);
        
        fuel1 = new Text();
        fuel1.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        fuel1.setX(readingX);
        fuel1.setY(100+3*carReadingsGap);
        fuel1.setText("Fuel:- ");
        childrenList.add(fuel1);
        
        fuelValue1 = new Text("not available");
        fuelValue1.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        fuelValue1.setX(readingX+80);
        fuelValue1.setY(100+3*carReadingsGap);
        childrenList.add(fuelValue1);
        
        
    }
    
    private void setCarInfo2()
    {
        car2 = new Text();
        car2.setFont(Font.font("times new roman", FontPosture.ITALIC, 25));
        car2.setX(carsTitleX);
        car2.setY(250);
        car2.setText("Car 2");
        car2.setFill(Color.LIGHTBLUE);
        childrenList.add(car2);
        
        speed2 = new Text();
        speed2.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        speed2.setX(readingX);
        speed2.setY(250+carReadingsGap);
        speed2.setText("Speed:- ");
        speed2.setFill(Color.LIGHTBLUE);        
        childrenList.add(speed2);
        
        speedValue2= new Text("not available");
        speedValue2.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        speedValue2.setX(readingX+80);
        speedValue2.setY(250+carReadingsGap);
        speedValue2.setFill(Color.LIGHTBLUE);
        childrenList.add(speedValue2);
        
        rpm2 = new Text();
        rpm2.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        rpm2.setX(readingX);
        rpm2.setY(250+2*carReadingsGap);
        rpm2.setText("RPM:- ");
        rpm2.setFill(Color.LIGHTBLUE);
        childrenList.add(rpm2);
        
        rpmValue2= new Text("not available");
        rpmValue2.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        rpmValue2.setX(readingX+80);
        rpmValue2.setY(250+2*carReadingsGap);
        rpmValue2.setFill(Color.LIGHTBLUE);
        childrenList.add(rpmValue2);
        
        fuel2 = new Text();
        fuel2.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        fuel2.setX(readingX);
        fuel2.setY(250+3*carReadingsGap);
        fuel2.setText("Fuel:- ");
        fuel2.setFill(Color.LIGHTBLUE);
        childrenList.add(fuel2);
        
        fuelValue2= new Text("not available");
        fuelValue2.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        fuelValue2.setX(readingX+80);
        fuelValue2.setY(250+3*carReadingsGap);
        fuelValue2.setFill(Color.LIGHTBLUE);
        childrenList.add(fuelValue2);
    }
    
    private void setCarInfo3()
    {
        car3 = new Text();
        car3.setFont(Font.font("times new roman", FontPosture.ITALIC, 25));
        car3.setX(carsTitleX);
        car3.setY(400);
        car3.setText("Car 3");
        car3.setFill(Color.LIGHTCORAL);
        childrenList.add(car3);
        
        speed3 = new Text();
        speed3.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        speed3.setX(readingX);
        speed3.setY(400+carReadingsGap);
        speed3.setText("Speed:- ");
        speed3.setFill(Color.LIGHTCORAL);
        childrenList.add(speed3);
        
        speedValue3 = new Text("not available");
        speedValue3.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        speedValue3.setX(readingX+80);
        speedValue3.setY(400+carReadingsGap);
        speedValue3.setFill(Color.LIGHTCORAL);
        childrenList.add(speedValue3);
        
        rpm3 = new Text();
        rpm3.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        rpm3.setX(readingX);
        rpm3.setY(400+2*carReadingsGap);
        rpm3.setText("RPM:- ");
        rpm3.setFill(Color.LIGHTCORAL);
        childrenList.add(rpm3);
        
        rpmValue3= new Text("not available");
        rpmValue3.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        rpmValue3.setX(readingX+80);
        rpmValue3.setY(400+2*carReadingsGap);
        rpmValue3.setFill(Color.LIGHTCORAL);
        childrenList.add(rpmValue3);
        
        fuel3 = new Text();
        fuel3.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        fuel3.setX(readingX);
        fuel3.setY(400+3*carReadingsGap);
        fuel3.setText("Fuel:- ");
        fuel3.setFill(Color.LIGHTCORAL);
        childrenList.add(fuel3);
        
        fuelValue3= new Text("not available");
        fuelValue3.setFont(Font.font("times new roman", FontPosture.ITALIC, 20));
        fuelValue3.setX(readingX+80);
        fuelValue3.setY(400+3*carReadingsGap);
        fuelValue3.setFill(Color.LIGHTCORAL);
        childrenList.add(fuelValue3);
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
        switch (tripID) 
        {
            case 3:
            {
                System.out.println("UpdateUI: tripid is 3 ");
                updateCarInfo1(kafkaRecord);
                break;
            }
            case 4:
            {
                System.out.println("UpdateUI: tripid is 4" );
                updateCarInfo2(kafkaRecord);
                break;
            }
            case 5:
            {
                System.out.println("UpdateUI: tripid is 5");
                updateCarInfo3(kafkaRecord);
                break;
            }
            default:
                break;
        }
        
    }

    private static void updateCarInfo1(KafkaRecord kafkaRecord)
    {
        speedValue1.setText(Double.toString(kafkaRecord.getSpeed()));
        rpmValue1.setText(Double.toString(kafkaRecord.getRpm()));
        fuelValue1.setText(Double.toString(kafkaRecord.getFuel())+"%");
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position( new LatLong(kafkaRecord.getLatitude(), kafkaRecord.getLongitude()) )
                .visible(Boolean.TRUE)
                .title("Car 1");
        Marker marker = new Marker( markerOptions );
        map.addMarker(marker);
    }
    
    private static void updateCarInfo2(KafkaRecord kafkaRecord)
    {
        speedValue2.setText(Double.toString(kafkaRecord.getSpeed()));
        rpmValue2.setText(Double.toString(kafkaRecord.getRpm()));
        fuelValue2.setText(Double.toString(kafkaRecord.getFuel())+"%");

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position( new LatLong(kafkaRecord.getLatitude(), kafkaRecord.getLongitude()) )
                .visible(Boolean.TRUE)
                .title("Car 2");
        Marker marker = new Marker( markerOptions );
        map.addMarker(marker);
    }
    
    private static void updateCarInfo3(KafkaRecord kafkaRecord)
    {
        speedValue3.setText(Double.toString(kafkaRecord.getSpeed()));
        rpmValue3.setText(Double.toString(kafkaRecord.getRpm()));
        fuelValue3.setText(Double.toString(kafkaRecord.getFuel())+"%");
        
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position( new LatLong(kafkaRecord.getLatitude(), kafkaRecord.getLongitude()) )
                .visible(Boolean.TRUE)
                .title("Car 3");
        Marker marker = new Marker( markerOptions );
        map.addMarker(marker);
    }
    
}
