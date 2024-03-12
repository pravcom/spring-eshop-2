package com.akhtyamov.springintegration.service;

import com.akhtyamov.springintegration.domain.Order;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class OrderServiceImpl implements OrderService{


    @Override
    public void save(Order order) {
        File orderFolder = new File("C:\\Users\\DAkhtyamov\\Desktop\\java\\eshop\\orders");

        File orderFile = new File(orderFolder, order.getOrderId() + ".json");
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(orderFile,order);
        } catch (StreamWriteException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
