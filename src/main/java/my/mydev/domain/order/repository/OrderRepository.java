package my.mydev.domain.order.repository;

import my.mydev.domain.order.domain.Order;
import my.mydev.domain.order.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByMemberIdOrderByOrderDateDesc(Long memberId);
   /* // 특정 주문의 모든 주문 상품 조회
    List<OrderItem> findByOrderId(Long orderId);

    // 특정 상품의 전체 주문 수량 조회
    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi WHERE oi.product.id = :productId")
    Integer getTotalQuantityByProductId(@Param("productId") Long productId);

    // 특정 주문의 총 금액 계산
    @Query("SELECT SUM(oi.price * oi.quantity) FROM OrderItem oi WHERE oi.order.id = :orderId")
    Integer calculateOrderTotal(@Param("orderId") Long orderId);*/

}
