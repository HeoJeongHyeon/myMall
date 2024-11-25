package my.mydev.domain.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.mydev.domain.cart.dto.CartItemDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CartOrderDto {

    private List<CartItemDto> cartItems;
    private int totalAmount;
    private String memberName;

}
