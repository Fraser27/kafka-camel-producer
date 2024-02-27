package com.fraser.camel;

import java.util.Date;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer extends RouteBuilder {

    @Autowired
    private KafkaProperties kafkaProps;

    public static final String ADD_ORDER_ROUTE = "direct:addOrder";

    @Override
    public void configure() throws Exception {
        // String uri =
        // "kafka:tweets?brokers=10.0.38.95:9092&consumersCount=10&autoOffsetReset=latest&groupId=tweet-analytics";
        from(ADD_ORDER_ROUTE)
                .routeId("Add an Order")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exch1) throws Exception {
                        String date = String.valueOf(new Date());
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put("orderId", String.valueOf(Math.random()));
                        jsonObj.put("log_date", date);
                        @SuppressWarnings("unchecked")
                        Map<String, Object> event = exch1.getMessage().getBody(Map.class);
                        jsonObj.put("data", event);
                        String orderId = "0";
                        if (event.containsKey("orderId")) {
                            orderId = event.get("orderId").toString();
                        }
                        exch1.getIn().setBody(jsonObj);
                        System.out.println(jsonObj);
                        exch1.getIn().setHeader(KafkaConstants.KEY, orderId);
                    }
                })
                .to(kafkaProps.getOrders());

        // Fixed publishing every 5 millis
        from("timer://foo?fixedRate=true&period=5")
        .routeId("bulk-order")
        .autoStartup(false)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exch1) throws Exception {
                        String date = String.valueOf(new Date());
                        JSONObject jsonObj = new JSONObject();
                        //jsonObj.put("orderId", String.valueOf(Math.random()));
                        jsonObj.put("log_date", date);
                        @SuppressWarnings("unchecked")
                        Map<String, Object> event = exch1.getMessage().getBody(Map.class);
                        String orderId = "0";
                        if (event.containsKey("orderId")) {
                            orderId = event.get("orderId").toString();
                        }
                        jsonObj.put("data", event);
                        exch1.getIn().setBody(jsonObj);
                        System.out.println(jsonObj);
                        exch1.getIn().setHeader(KafkaConstants.KEY, orderId);
                    }
                })
                .to(kafkaProps.getOrders());

    }
}
