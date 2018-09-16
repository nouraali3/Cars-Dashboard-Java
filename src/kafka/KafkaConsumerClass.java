
package kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;

public class KafkaConsumerClass 
{
    private final static String TOPIC = "car-data";
    //ip:port of the server to which we connect 
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
    
    
    private static void runConsumer() throws InterruptedException 
    {
        final Consumer<Long, String> consumer = createConsumer();
        final int giveUp = 100;   int noRecordsCount = 0;
        while (true) 
        {
            //poll => fetches the un committed records timeouts after 1000 ms
            final ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
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
                System.out.printf("Consumer Record:( %s)\n" , record.value());
                HashMap<String,String> tripInfo  = new HashMap<>();
                tripInfo = getInfo(record);
                updateFields(tripInfo);
            });
            
            // to mark the list of received records before failure  ....so the next poll fetches the un committed records
            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("DONE");
    }
    
    private static HashMap<String,String> getInfo(ConsumerRecord record)
    {
        HashMap<String,String> tripInfo  = new HashMap<>();
        
        
        
        return tripInfo;
    }
    
    private static void updateFields(HashMap<String, String> tripInfo) 
    {
        //if tripID == trip3
        
        //else if tripID == trip4
        
        //else tripID == trip5
    }
    
    public static void main(String... args) throws Exception 
    {
        runConsumer();
    }
    
}
