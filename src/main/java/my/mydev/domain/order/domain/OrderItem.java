package my.mydev.domain.order.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.mydev.domain.Product.domain.Product;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer quantity;

    private Integer price;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void setOrder(Order order) {
        this.order = order;
    }
}
