package com.fraser.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.ValueBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.component.kafka.KafkaManualCommit;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.support.ExpressionAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fraser.businesslogic.OrderProcessor;

@Component
public class KafkaConsumer extends RouteBuilder{

    @Autowired
    private KafkaProperties kafkaProps;

    @Autowired
    private OrderProcessor processor;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void configure() throws Exception {
        // Consumers
        from(kafkaProps.getOrders())
        .routeId("order-intake")
        .log("Order intake ${body}")
        .unmarshal().json(JsonLibrary.Jackson, Map.class)
        //.aggregate(new GroupedBodyAggregationStrategy())
        //.constant(true)
        // .completionSize(100)
        // .completionTimeout(500)
        // .process(new Processor() {
        //     @Override
        //     public void process(Exchange exch1) throws Exception {
        //         System.out.println("-- Orders consumer running ");
        //         System.out.println(exch1.getIn().getBody());
        //     }           
        // })
        .bean(OrderProcessor.class, "processor");
        //.log("Consumer is running");

        from(kafkaProps.getAnalytics())
        .routeId("analytics-intake")
        .autoStartup(false)
        .unmarshal().json(JsonLibrary.Jackson, Map.class)
        .bean(OrderProcessor.class, "processor");


        from(kafkaProps.getS3dump())
        .routeId("s3-dump-intake")
        .autoStartup(false)
        .unmarshal().json(JsonLibrary.Jackson, Map.class)
        .bean(OrderProcessor.class, "processor");
 
        
        from(kafkaProps.getSubscription())
        .routeId("send-to-email-sms-whatsapp-topic-1")
        .autoStartup(true)
        .unmarshal().json(JsonLibrary.Jackson, Map.class)
        .bean(OrderProcessor.class, "mapToJson")
        .multicast().parallelProcessing()
        .to(kafkaProps.getEmail1(), kafkaProps.getSms1(), kafkaProps.getWhatsapp1());

        from(kafkaProps.getSubscription())
        .routeId("send-to-email-sms-whatsapp-topic-2")
        .autoStartup(false)
        .unmarshal().json(JsonLibrary.Jackson, Map.class)
        .bean(OrderProcessor.class, "mapToJson")
        .multicast().parallelProcessing()
        .to(kafkaProps.getEmail2(), kafkaProps.getSms2(), kafkaProps.getWhatsapp2());

        from(kafkaProps.getEmail1())
        .routeId("fetch-from-emails-topic-1")
        .autoStartup(true)
        .unmarshal().json(JsonLibrary.Jackson, Map.class)
        .bean(OrderProcessor.class, "processor");

        from(kafkaProps.getEmail2())
        .routeId("fetch-from-emails-topic-2")
        .autoStartup(false)
        .unmarshal().json(JsonLibrary.Jackson, Map.class)
        .bean(OrderProcessor.class, "processor");

        from(kafkaProps.getSms1())
        .routeId("fetch-from-sms-topic-1")
        .autoStartup(true)
        .unmarshal().json(JsonLibrary.Jackson, Map.class)
        .bean(OrderProcessor.class, "processor");

        from(kafkaProps.getSms2())
        .routeId("fetch-from-sms-topic-2")
        .autoStartup(false)
        .unmarshal().json(JsonLibrary.Jackson, Map.class)
        .bean(OrderProcessor.class, "processor");

        from(kafkaProps.getWhatsapp1())
        .routeId("fetch-from-whatsapp-topic-1")
        .autoStartup(true)
        .unmarshal().json(JsonLibrary.Jackson, Map.class)
        .bean(OrderProcessor.class, "processor");

        from(kafkaProps.getWhatsapp2())
        .routeId("fetch-from-whatsapp-topic-2")
        .autoStartup(false)
        .unmarshal().json(JsonLibrary.Jackson, Map.class)
        .bean(OrderProcessor.class, "processor");


        from(kafkaProps.getNewarrivals()).routeId("send-new-arrivals-details")
        .autoStartup(true)
        .unmarshal().json(JsonLibrary.Jackson, Map.class)
        .bean(OrderProcessor.class, "mapToJson")
        .multicast().parallelProcessing()
		.to(kafkaProps.getEmail1(),kafkaProps.getSms1(), kafkaProps.getWhatsapp1());
    }
    
}
