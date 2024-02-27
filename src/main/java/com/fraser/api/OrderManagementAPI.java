package com.fraser.api;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.camel.ProducerTemplate;
/**
 * @author fraser.sequeira
 *
 */

@Service
@Data
@AllArgsConstructor
@Slf4j

public class OrderManagementAPI {
    private ProducerTemplate template;

    // write a springboot POST method to add an order
    @PostMapping(value = "/order", produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "Add an order", notes = "Add an order to the database", tags = "order-management")
    public void addOrder(@RequestBody Map<String, Object> order) {
        log.info("action=add_an_order, order="+ order);
        template.sendBody("direct:addOrder", order);
    }   
    

}
