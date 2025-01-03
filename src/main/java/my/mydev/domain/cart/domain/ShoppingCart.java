package my.mydev.domain.cart.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import my.mydev.domain.member.domain.Member;

import java.util.List;

@Entity
@Setter
@Getter
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_Id")
    private Member member;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;
}
