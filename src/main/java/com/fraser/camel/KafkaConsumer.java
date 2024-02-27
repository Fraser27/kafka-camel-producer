package com.fraser.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
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
        .bean(OrderProcessor.class, "processor");
        //.log("Consumer is running");

        from(kafkaProps.getAnalytics())
        .routeId("analytics-intake")
        .autoStartup(false)
        .bean(OrderProcessor.class, "processor");


        from(kafkaProps.getS3dump())
        .routeId("s3-dump-intake")
        .autoStartup(false)
        .bean(OrderProcessor.class, "processor");
 
        
        from(kafkaProps.getSubscription())
        .routeId("send-to-email-topic-1")
        .autoStartup(true)
        .to(kafkaProps.getEmail1());

        from(kafkaProps.getSubscription())
        .routeId("send-to-email-topic-2")
        .autoStartup(false)
        .to(kafkaProps.getEmail2());

        from(kafkaProps.getSubscription())
        .routeId("send-to-sms-topic-1")
        .autoStartup(true)
        .to(kafkaProps.getSms1());

        from(kafkaProps.getSubscription())
        .routeId("send-to-sms-topic-2")
        .autoStartup(false)
        .to(kafkaProps.getSms2());

        from(kafkaProps.getSubscription())
        .routeId("send-to-whatsapp-topic-1")
        .autoStartup(true)
        .to(kafkaProps.getWhatsapp1());

        from(kafkaProps.getSubscription())
        .routeId("send-to-whatsapp-topic-2")
        .autoStartup(false)
        .to(kafkaProps.getWhatsapp2());

        from(kafkaProps.getEmail1())
        .routeId("fetch-from-emails-topic-1")
        .autoStartup(true)
        .bean(OrderProcessor.class, "processor");

        from(kafkaProps.getEmail2())
        .routeId("fetch-from-emails-topic-2")
        .autoStartup(false)
        .bean(OrderProcessor.class, "processor");

        from(kafkaProps.getSms1())
        .routeId("fetch-from-sms-topic-1")
        .autoStartup(true)
        .bean(OrderProcessor.class, "processor");

        from(kafkaProps.getSms2())
        .routeId("fetch-from-sms-topic-2")
        .autoStartup(false)
        .bean(OrderProcessor.class, "processor");

        from(kafkaProps.getWhatsapp1())
        .routeId("fetch-from-whatsapp-topic-1")
        .autoStartup(true)
        .bean(OrderProcessor.class, "processor");

        from(kafkaProps.getWhatsapp2())
        .routeId("fetch-from-whatsapp-topic-2")
        .autoStartup(false)
        .bean(OrderProcessor.class, "processor");
    }
    
}
