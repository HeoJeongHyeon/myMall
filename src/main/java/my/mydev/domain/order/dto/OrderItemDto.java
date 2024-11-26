package my.mydev.domain.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderItemDto {
    private String productName;
    private int quantity;
    private int price;
}
