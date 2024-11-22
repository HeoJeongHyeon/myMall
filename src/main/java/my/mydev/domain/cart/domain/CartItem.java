package my.mydev.domain.cart.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import my.mydev.domain.Product.domain.Product;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private ShoppingCart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    private int price;

    // 기본 생성자, Getter, Setter
}
