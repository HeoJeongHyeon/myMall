package my.mydev.domain.cart.repository;

import my.mydev.domain.cart.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    CartItem findByCartIdAndProductId(Long id, Long productId);

}

