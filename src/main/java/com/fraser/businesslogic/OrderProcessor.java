package com.fraser.businesslogic;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Data
public class OrderProcessor {
    
    public void processor(Map<String, Object> order) {
        try {
            Thread.sleep(1000);
            log.info("processing_order:" + order);
            // Store in Opensearch here
        } catch (InterruptedException e) {
            log.error("processing_order_error:" + e.getMessage());
        }
    }

}
