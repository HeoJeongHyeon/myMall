package my.mydev.domain.order.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.mydev.domain.address.domain.Address;
import my.mydev.domain.member.domain.Member;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "orders")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    * 연관관계에서는 다쪽이 항상 외래키를 가진다.
    * 회원과 주문
    * 한 회원은 여러개의 주문을 할 수 있으며, 주문은 한 회원으로부터 주문된다.
    * 회원 1 : N 주문
    * 연관관계의 주인이 하는 설정 mappedBy를 여기서 쓰면 안됌.
    * */
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    private LocalDateTime orderDate;

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        orderItems.forEach(orderItem -> orderItem.setOrder(this));
    }
}
