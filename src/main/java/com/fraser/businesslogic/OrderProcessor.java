package com.fraser.businesslogic;

import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fraser.camel.KafkaProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Data
public class OrderProcessor {
    private ObjectMapper mapper;
    private ProducerTemplate template;
    private KafkaProperties kafkaProps;
    
    public void processor(Map<String, Object> order) {
        try {
            Thread.sleep(1000);
            log.info("processing_order:" + order);
            // Store in Opensearch here
        } catch (InterruptedException e) {
            log.error("processing_order_error:" + e.getMessage());
        }
    }


    public String mapToJson(Map<String, Object> data) throws JsonProcessingException {
        System.out.println("method=mapToJson Map="+data);
        String string_json = mapper.writeValueAsString(data);
        System.out.println("mapToJson Map="+data+" , string_json="+string_json);
        template.asyncSendBody(kafkaProps.getEmail1(), string_json);
        template.asyncSendBody(kafkaProps.getSms1(), string_json);
        template.asyncSendBody(kafkaProps.getWhatsapp1(), string_json);
        return string_json;
        
    }

}
