package com.fraser.camel;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("local.kafka")
public class KafkaProperties {

    private String orders;
    private String notifications1;
    private String notifications2;

    /**
     * @return the topic
     */
    public String getOrders() {
        System.out.println(orders);
        return orders;
    }

    /**
     * @param topic the topic to set
     */
    public void setOrders(String orders) {
        this.orders = orders;
    }

    /**
     * @return the notifications1
     */
    public String getNotifications1() {
        return notifications1;
    }

    /**
     * @param notifications1 the notifications1 to set
     */
    public void setNotifications1(String notifications1) {
        this.notifications1 = notifications1;
    }

    /**
     * @return the notifications2
     */
    public String getNotifications2() {
        return notifications2;
    }

    /**
     * @param notifications2 the notifications2 to set
     */
    public void setNotifications2(String notifications2) {
        this.notifications2 = notifications2;
    }






}