package my.mydev.domain.Product.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.mydev.domain.order.domain.OrderItem;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private int price;
    @NotNull
    private int stockQuantity;

    /*@ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;*/

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @Builder
    public Product(String name, String description, int price, int stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // 상품 정보 수정
    public void update(String name, String description, int price, int stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // 재고 감소
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new IllegalArgumentException("재고부족"); // 수정필요
        }
        this.stockQuantity = restStock;
    }

    // 재고 증가
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }
}

