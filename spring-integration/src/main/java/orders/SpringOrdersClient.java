package orders;

import com.akhtyamov.springintegration.domain.Order;
import com.akhtyamov.springintegration.domain.OrderDetails;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class SpringOrdersClient {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringOrdersClient.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8084"));
        ConfigurableApplicationContext context = app.run(args);

        OrderIntegrationConfig config = context.getBean("orderIntegrationConfig", OrderIntegrationConfig.class);

        DirectChannel ordersChannel = config.getOrdersChannel();
        //Формируем сообщение
//        Order order = new Order();
//        order.setOrderId(2);
//        order.setUsername("userName");
//        order.setAddress("address");
//
//        List<OrderDetails> orderDetails = new ArrayList<>();
//        orderDetails.add(OrderDetails.builder().product("Milk").price(10.0).amount(2.0).sum(20.0).build());
//        orderDetails.add(OrderDetails.builder().product("Cheese").price(20.0).amount(3.0).sum(60.0).build());
//
//        order.setDetails(orderDetails);
//
//        Message<Order> message = MessageBuilder
//                .withPayload(order)
//                .setHeader("Content-type", "application/json")
//                .build();
//
//        ordersChannel.send(message);
    }
}
