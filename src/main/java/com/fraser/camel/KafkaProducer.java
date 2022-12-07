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
public class KafkaProducer extends RouteBuilder{

    @Autowired
    private KafkaProperties kafkaProps;

    @Override
    public void configure() throws Exception {
        // String uri = "kafka:tweets?brokers=10.0.38.95:9092&consumersCount=10&autoOffsetReset=latest&groupId=tweet-analytics";
        
        // Fixed Timer publishing to Kafka every 100 ms
        from("timer://foo?fixedRate=true&period=100")
        .routeId("Producer 1")
        .process(new Processor() {
            @Override
            public void process(Exchange exch1) throws Exception {
                String date = String.valueOf(new Date());
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("tweetId", String.valueOf(Math.random()));
                jsonObj.put("date", date);
                jsonObj.put("message", String.valueOf("Hello from Producer 1"));
                exch1.getIn().setBody(jsonObj);
                System.out.println(jsonObj);
                exch1.getIn().setHeader(KafkaConstants.KEY, date);
                System.out.println(kafkaProps.getTopic());
            }           
        })
        .to(kafkaProps.getTopic());


        // Fixed Timer publishing to Kafka every 100 ms
        from("timer://foo?fixedRate=true&period=100")
        .routeId("Producer 2")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exch1) throws Exception {
                        String date = String.valueOf(new Date());
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put("tweetId", String.valueOf(Math.random()));
                        jsonObj.put("date", date);
                        jsonObj.put("message", String.valueOf("Hello from Producer 2"));
                        exch1.getIn().setBody(jsonObj);
                        System.out.println(jsonObj);
                        exch1.getIn().setHeader(KafkaConstants.KEY, date);
                        System.out.println(kafkaProps.getTopic());
                    }           
                })
                .to(kafkaProps.getTopic());
    }
    
}
