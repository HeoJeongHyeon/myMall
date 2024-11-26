package my.mydev.domain.cart.repository;

import my.mydev.domain.cart.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    CartItem findByCartIdAndProductId(Long id, Long productId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.member.id = :memberId AND ci.id IN :cartItemIds")
    List<CartItem> findByMemberIdAndIdIn(@Param("memberId") Long memberId, @Param("cartItemIds") List<Long> cartItemIds);


//    List<CartItem> findByMemberIdAndIdIn(Long memberId, List<Long> cartItemIds);


}

