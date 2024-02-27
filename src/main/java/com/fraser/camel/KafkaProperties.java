package com.fraser.camel;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("local.kafka")
public class KafkaProperties {

    private String orders;
    private String analytics;
    private String subscription;
    private String newarrivals;
    private String email1;
    private String email2;
    private String sms1;
    private String sms2;
    private String whatsapp1;
    private String whatsapp2;
    private String s3dump;

    /**
     * @return the orders
     */
    public String getOrders() {
        return orders;
    }
    /**
     * @return the s3dump
     */
    public String getS3dump() {
        return s3dump;
    }
    /**
     * @param s3dump the s3dump to set
     */
    public void setS3dump(String s3dump) {
        this.s3dump = s3dump;
    }
    /**
     * @param orders the orders to set
     */
    public void setOrders(String orders) {
        this.orders = orders;
    }
    /**
     * @return the analytics
     */
    public String getAnalytics() {
        return analytics;
    }
    /**
     * @param analytics the analytics to set
     */
    public void setAnalytics(String analytics) {
        this.analytics = analytics;
    }
    /**
     * @return the subscription
     */
    public String getSubscription() {
        return subscription;
    }
    /**
     * @param subscription the subscription to set
     */
    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }
    /**
     * @return the newarrivals
     */
    public String getNewarrivals() {
        return newarrivals;
    }
    /**
     * @param newarrivals the newarrivals to set
     */
    public void setNewarrivals(String newarrivals) {
        this.newarrivals = newarrivals;
    }
    /**
     * @return the email1
     */
    public String getEmail1() {
        return email1;
    }
    /**
     * @param email1 the email1 to set
     */
    public void setEmail1(String email1) {
        this.email1 = email1;
    }
    /**
     * @return the email2
     */
    public String getEmail2() {
        return email2;
    }
    /**
     * @param email2 the email2 to set
     */
    public void setEmail2(String email2) {
        this.email2 = email2;
    }
    /**
     * @return the sms1
     */
    public String getSms1() {
        return sms1;
    }
    /**
     * @param sms1 the sms1 to set
     */
    public void setSms1(String sms1) {
        this.sms1 = sms1;
    }
    /**
     * @return the sms2
     */
    public String getSms2() {
        return sms2;
    }
    /**
     * @param sms2 the sms2 to set
     */
    public void setSms2(String sms2) {
        this.sms2 = sms2;
    }
    /**
     * @return the whatsapp1
     */
    public String getWhatsapp1() {
        return whatsapp1;
    }
    /**
     * @param whatsapp1 the whatsapp1 to set
     */
    public void setWhatsapp1(String whatsapp1) {
        this.whatsapp1 = whatsapp1;
    }
    /**
     * @return the whatsapp2
     */
    public String getWhatsapp2() {
        return whatsapp2;
    }
    /**
     * @param whatsapp2 the whatsapp2 to set
     */
    public void setWhatsapp2(String whatsapp2) {
        this.whatsapp2 = whatsapp2;
    }

    


    






}