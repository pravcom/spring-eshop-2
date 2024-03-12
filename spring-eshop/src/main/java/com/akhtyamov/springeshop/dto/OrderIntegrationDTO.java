package com.akhtyamov.springeshop.dto;

import com.akhtyamov.springeshop.domain.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderIntegrationDTO {
    private Long orderId;
    private  String username;
    private String address;
    private List<OrderDetailsDTO> details;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDetailsDTO{
        private String product;
        private Double price;
        private Double amount;
        private Double sum;

        public OrderDetailsDTO(OrderDetail detail) {
            this.product = detail.getProduct().getTitle();
            this.price = detail.getPrice().doubleValue();
            this.amount = detail.getAmount().doubleValue();
            this.sum = detail.getPrice().multiply(detail.getAmount()).doubleValue();
        }
    }
}
