package orders;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.channel.DirectChannel;

@Configuration("orderIntegrationConfig")
@ImportResource("/integration/http-orders-integration.xml")
public class OrderIntegrationConfig {
    private DirectChannel ordersChannel;

    public OrderIntegrationConfig(DirectChannel ordersChannel) {
        this.ordersChannel = ordersChannel;
    }

    public DirectChannel getOrdersChannel() {
        return ordersChannel;
    }
}
