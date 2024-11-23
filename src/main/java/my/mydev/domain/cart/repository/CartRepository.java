package my.mydev.domain.cart.repository;

import jakarta.persistence.Id;
import my.mydev.domain.cart.domain.CartItem;
import my.mydev.domain.cart.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<ShoppingCart, Id> {


    Optional<ShoppingCart> findByMemberId(Long memberId);

//    void deleteByCartId(Long cartId);


}
