package com.fraser.camel;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.processor.aggregate.GroupedBodyAggregationStrategy;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fraser.businesslogic.OrderProcessor;

@Component
public class KafkaConsumer extends RouteBuilder{

    @Autowired
    private KafkaProperties kafkaProps;

    @Autowired
    private OrderProcessor processor;

    @Override
    public void configure() throws Exception {
        // Consumers
        from(kafkaProps.getOrders())
        .routeId("order-intake")
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
        .bean(OrderProcessor.class, "processOrders");
        //.log("Consumer is running");


    }
    
}
