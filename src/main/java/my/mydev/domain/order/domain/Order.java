package my.mydev.domain.order.domain;


import jakarta.persistence.*;
import my.mydev.domain.member.domain.Member;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "orders")
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


    private LocalDateTime orderDate;

    // ENUM 타입 반드시 String으로 지정해야 중복오류 없음.
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
