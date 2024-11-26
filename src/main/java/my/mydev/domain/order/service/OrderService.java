package my.mydev.domain.order.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.mydev.domain.address.domain.Address;
import my.mydev.domain.address.dto.AddressDto;
import my.mydev.domain.address.repository.AddressRepository;
import my.mydev.domain.cart.domain.CartItem;
import my.mydev.domain.cart.repository.CartItemRepository;
import my.mydev.domain.cart.repository.CartRepository;
import my.mydev.domain.member.domain.Member;
import my.mydev.domain.member.repository.MemberRepository;
import my.mydev.domain.order.domain.Order;
import my.mydev.domain.order.domain.OrderItem;
import my.mydev.domain.order.dto.OrderDto;
import my.mydev.domain.order.dto.OrderItemDto;
import my.mydev.domain.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    public Long createOrder(Long memberId, List<Long> cartItemIds, Long addressId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("멤버없음"));
        log.info("member = {} ", member.getId());
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("주소없음."));
        log.info("cartItemIds = {} ", cartItemIds);

        List<CartItem> cartItems = cartItemRepository.findByMemberIdAndIdIn(memberId, cartItemIds);
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("장바구니가 비어있습니다.");
        }
        log.info("cartItems 개수 = {}", cartItems.size());


        Order order = Order.builder()
                .member(member)
                .address(address) //추가변경
                .orderDate(LocalDateTime.now())
                .build();
// 주문 상품 생성
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> OrderItem.builder()
                        .product(cartItem.getProduct())
                        .quantity(cartItem.getQuantity())
                        .price(cartItem.getProduct().getPrice())
                        .build())
                .collect(Collectors.toList());

        // 주문과 주문 상품 연관관계 설정
        order.setOrderItems(orderItems);

        // 주문 저장
        Order savedOrder = orderRepository.save(order);

        return savedOrder.getId();
    }

    public List<OrderDto> getOrdersByMemberId(Long id) {
        List<Order> orders = orderRepository.findByMemberIdOrderByOrderDateDesc(id);
        return orders.stream()
                .map(order->OrderDto.builder()
                        .id(order.getId())
                        .orderDate(order.getOrderDate())
                        .orderItems(order.getOrderItems().stream()
                                .map(item-> OrderItemDto.builder()
                                        .productName(item.getProduct().getName())
                                        .quantity(item.getQuantity())
                                        .price(item.getPrice())
                                        .build()).collect(Collectors.toList()))
                        .address(AddressDto.from(order.getAddress()))
                        .totalAmount(order.getOrderItems().stream()
                                .mapToInt(item->item.getPrice()*item.getQuantity())
                                .sum())
                        .build())
                .collect(Collectors.toList());
    }
}
