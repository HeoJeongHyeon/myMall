package my.mydev.domain.order.dto;

import lombok.*;
import my.mydev.domain.address.dto.AddressDto;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderDto {
    private Long id;
    private LocalDateTime orderDate;
    private List<OrderItemDto> orderItems;
    private AddressDto address;
    private int totalAmount;
}

