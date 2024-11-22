package my.mydev.domain.cart.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class CartItemDto {
    private Long id;
    private String productName;
    private Integer quantity;
    private int price;
    private int totalPrice;


}
