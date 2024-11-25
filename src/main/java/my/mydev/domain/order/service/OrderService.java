package my.mydev.domain.order.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import my.mydev.domain.cart.domain.CartItem;
import my.mydev.domain.cart.repository.CartRepository;
import my.mydev.domain.order.dto.CartOrderDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    /*public CartOrderDto getCartOrderDetails(Long id, List<Long> cartItemIds) {
    }*/
}
