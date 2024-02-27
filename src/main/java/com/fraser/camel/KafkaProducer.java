package com.fraser.camel;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

@Component
public class KafkaProducer extends RouteBuilder {

    @Autowired
    private KafkaProperties kafkaProps;

    public static final String ADD_ORDER_ROUTE = "direct:addOrder";
    List<String> categoryList = Arrays.asList("shoes", "clothes", "electronics", "toys");
    public static Random rand = new Random();
    public static Faker faker = new Faker();

    @Override
    public void configure() throws Exception {
        // String uri =
        // "kafka:tweets?brokers=10.0.38.95:9092&consumersCount=10&autoOffsetReset=latest&groupId=tweet-analytics";
        from(ADD_ORDER_ROUTE)
                .routeId("Single Order")
                .autoStartup(false)
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exch1) throws Exception {
                                String date = String.valueOf(new Date());
                                JSONObject jsonObj = new JSONObject();
                                JSONObject jsonInnerObj = new JSONObject();
                                String orderId=String.valueOf(Math.random());
                                jsonInnerObj.put("orderId", orderId);
                                int randomIndex = rand.nextInt(categoryList.size());
                                jsonInnerObj.put("product_type", categoryList.get(randomIndex));
                                jsonInnerObj.put("amount", Math.random());
                                jsonInnerObj.put("first_name", faker.name().firstName());
                                jsonInnerObj.put("last_name", faker.name().lastName());
                                jsonInnerObj.put("longitute", faker.address().longitude());
                                jsonInnerObj.put("latitude", faker.address().latitude());
                                jsonInnerObj.put("shipping_city", faker.address().city());
                                jsonInnerObj.put("shipping_state", faker.address().state());
                                jsonInnerObj.put("shipping_country", faker.address().country());
                                jsonInnerObj.put("product_name", faker.superhero().descriptor());
                                jsonInnerObj.put("shipping_phone_number", faker.phoneNumber().cellPhone());
                                jsonInnerObj.put("shipping_street", faker.address().streetAddress());
                                //jsonObj.put("orderId", String.valueOf(Math.random()));
                                jsonObj.put("log_date", date);
                                
                                jsonObj.put("data", jsonInnerObj);
                                exch1.getIn().setBody(jsonObj);
                                System.out.println(jsonObj);
                                exch1.getIn().setHeader(KafkaConstants.KEY, orderId);
                            }
                        })
                        .to(kafkaProps.getOrders());
        

        // Fixed publishing every 100 millis
        from("timer://foo?fixedRate=true&period=100")
        .routeId("order-every-100msec")
        .autoStartup(false)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exch1) throws Exception {
                        String date = String.valueOf(new Date());
                        JSONObject jsonObj = new JSONObject();
                        JSONObject jsonInnerObj = new JSONObject();
                        String orderId=String.valueOf(Math.random());
                        jsonInnerObj.put("orderId", orderId);
                        int randomIndex = rand.nextInt(categoryList.size());
                        jsonInnerObj.put("product_type", categoryList.get(randomIndex));
                        jsonInnerObj.put("amount", Math.random());
                        jsonInnerObj.put("first_name", faker.name().firstName());
                        jsonInnerObj.put("last_name", faker.name().lastName());
                        jsonInnerObj.put("longitute", faker.address().longitude());
                        jsonInnerObj.put("latitude", faker.address().latitude());
                        jsonInnerObj.put("shipping_city", faker.address().city());
                        jsonInnerObj.put("shipping_state", faker.address().state());
                        jsonInnerObj.put("shipping_country", faker.address().country());
                        jsonInnerObj.put("product_name", faker.superhero().descriptor());
                        jsonInnerObj.put("shipping_phone_number", faker.phoneNumber().cellPhone());
                        jsonInnerObj.put("shipping_street", faker.address().streetAddress());
                        //jsonObj.put("orderId", String.valueOf(Math.random()));
                        jsonObj.put("log_date", date);
                        
                        jsonObj.put("data", jsonInnerObj);
                        exch1.getIn().setBody(jsonObj);
                        System.out.println(jsonObj);
                        exch1.getIn().setHeader(KafkaConstants.KEY, orderId);
                    }
                })
                .to(kafkaProps.getOrders());

        
        from("timer://foo?fixedRate=true&period=1000")
        .routeId("order-every-1sec")
        .autoStartup(false)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exch1) throws Exception {
                        String date = String.valueOf(new Date());
                        JSONObject jsonObj = new JSONObject();
                        JSONObject jsonInnerObj = new JSONObject();
                        String orderId=String.valueOf(Math.random());
                        jsonInnerObj.put("orderId", orderId);
                        int randomIndex = rand.nextInt(categoryList.size());
                        jsonInnerObj.put("product_type", categoryList.get(randomIndex));
                        jsonInnerObj.put("amount", Math.random());
                        jsonInnerObj.put("first_name", faker.name().firstName());
                        jsonInnerObj.put("last_name", faker.name().lastName());
                        jsonInnerObj.put("longitute", faker.address().longitude());
                        jsonInnerObj.put("latitude", faker.address().latitude());
                        jsonInnerObj.put("shipping_city", faker.address().city());
                        jsonInnerObj.put("shipping_state", faker.address().state());
                        jsonInnerObj.put("shipping_country", faker.address().country());
                        jsonInnerObj.put("product_name", faker.superhero().descriptor());
                        jsonInnerObj.put("shipping_phone_number", faker.phoneNumber().cellPhone());
                        jsonInnerObj.put("shipping_street", faker.address().streetAddress());
                        //jsonObj.put("orderId", String.valueOf(Math.random()));
                        jsonObj.put("log_date", date);
                        
                        jsonObj.put("data", jsonInnerObj);
                        exch1.getIn().setBody(jsonObj);
                        System.out.println(jsonObj);
                        exch1.getIn().setHeader(KafkaConstants.KEY, orderId);
                    }
                })
                .to(kafkaProps.getOrders());
        

        from("timer://foo?fixedRate=true&period=10")
        .routeId("order-every-10ms")
        .autoStartup(false)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exch1) throws Exception {
                        String date = String.valueOf(new Date());
                        JSONObject jsonObj = new JSONObject();
                        JSONObject jsonInnerObj = new JSONObject();
                        String orderId=String.valueOf(Math.random());
                        jsonInnerObj.put("orderId", orderId);
                        int randomIndex = rand.nextInt(categoryList.size());
                        jsonInnerObj.put("product_type", categoryList.get(randomIndex));
                        jsonInnerObj.put("amount", Math.random());
                        jsonInnerObj.put("first_name", faker.name().firstName());
                        jsonInnerObj.put("last_name", faker.name().lastName());
                        jsonInnerObj.put("longitute", faker.address().longitude());
                        jsonInnerObj.put("latitude", faker.address().latitude());
                        jsonInnerObj.put("shipping_city", faker.address().city());
                        jsonInnerObj.put("shipping_state", faker.address().state());
                        jsonInnerObj.put("shipping_country", faker.address().country());
                        jsonInnerObj.put("product_name", faker.superhero().descriptor());
                        jsonInnerObj.put("shipping_phone_number", faker.phoneNumber().cellPhone());
                        jsonInnerObj.put("shipping_street", faker.address().streetAddress());
                        //jsonObj.put("orderId", String.valueOf(Math.random()));
                        jsonObj.put("log_date", date);
                        
                        jsonObj.put("data", jsonInnerObj);
                        exch1.getIn().setBody(jsonObj);
                        System.out.println(jsonObj);
                        exch1.getIn().setHeader(KafkaConstants.KEY, orderId);
                    }
                })
                .to(kafkaProps.getOrders());

    
        from("timer://foo?fixedRate=true&period=1000")
        .routeId("new-arrivals-every-1s")
        .autoStartup(false)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exch1) throws Exception {
                        String date = String.valueOf(new Date());
                        JSONObject jsonObj = new JSONObject();
                        JSONObject jsonInnerObj = new JSONObject();
                        String productId=String.valueOf(Math.random());
                        jsonInnerObj.put("productId", productId);
                        int randomIndex = rand.nextInt(categoryList.size());
                        jsonInnerObj.put("product_type", categoryList.get(randomIndex));
                        jsonInnerObj.put("amount", Math.random());
                        jsonInnerObj.put("product_name", faker.pokemon().name());
                        //jsonObj.put("orderId", String.valueOf(Math.random()));
                        jsonObj.put("log_date", date);
                        
                        jsonObj.put("data", jsonInnerObj);
                        exch1.getIn().setBody(jsonObj);
                        System.out.println(jsonObj);
                        exch1.getIn().setHeader(KafkaConstants.KEY, productId);
                    }
                })
                .to(kafkaProps.getNewarrivals());
    
        
  }

}