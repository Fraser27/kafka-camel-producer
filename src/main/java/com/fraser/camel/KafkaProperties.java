package com.fraser.camel;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("local.kafka")
public class KafkaProperties {

    private String topic;

    /**
     * @return the topic
     */
    public String getTopic() {
        System.out.println(topic);
        return topic;
    }

    /**
     * @param topic the topic to set
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }




}