package com.fraser.camel;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer extends RouteBuilder{

    @Autowired
    private KafkaProperties kafkaProps;

    @Override
    public void configure() throws Exception {
        // String uri = "kafka:tweets?brokers=10.0.38.95:9092&consumersCount=10&autoOffsetReset=latest&groupId=tweet-analytics";
        
         // Consumers
         from(kafkaProps.getTopic()).process(new Processor() {

            @Override
            public void process(Exchange exch1) throws Exception {
                Thread.sleep(3000);
                System.out.println("Consumer----");
                System.out.println(exch1.getIn().getBody());
            
            }           
        }).log("Consumer is running");
    }
    
}
