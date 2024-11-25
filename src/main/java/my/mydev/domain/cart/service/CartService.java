package my.mydev.domain.cart.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.mydev.domain.Product.domain.Product;
import my.mydev.domain.Product.repository.ProductRepository;
import my.mydev.domain.cart.domain.CartItem;
import my.mydev.domain.cart.domain.ShoppingCart;
import my.mydev.domain.cart.dto.CartItemDto;
import my.mydev.domain.cart.repository.CartItemRepository;
import my.mydev.domain.cart.repository.CartRepository;
import my.mydev.domain.member.domain.Member;
import my.mydev.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productrepository;
    private final CartItemRepository cartItemRepository;


    /* add 멤버 id, productid, 재고 전달 받음.*/
    @Transactional
    public void addCart(Long memberId, Long productId, Integer stockQuantity) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 못찾겠어요"));
        Product product = productrepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 못찾겠어요"));
        if (product.getStockQuantity() < stockQuantity) {
            throw new IllegalArgumentException("재고가 부족해요");
        }

        /* 카트가 없으면 새로 생성하여 멤버를 저장하고 해당 카트를 반환 */
        ShoppingCart shoppingCart = cartRepository.findByMemberId(memberId)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setMember(member);
                    return cartRepository.save(newCart);
                });

        CartItem existingItem = cartItemRepository.findByCartIdAndProductId(shoppingCart.getId(), productId);
        if (existingItem != null) {
            if (product.getStockQuantity() < existingItem.getQuantity() + stockQuantity) {
                throw new IllegalArgumentException("재고 부족");
            }
            existingItem.setQuantity(existingItem.getQuantity() + stockQuantity);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(shoppingCart);
            cartItem.setProduct(product);
            cartItem.setQuantity(stockQuantity);
            cartItem.setPrice(product.getPrice());
            cartItemRepository.save(cartItem);
        }
    }

    public List<CartItemDto> getCartItemsByIds(List<Long> cartItemIds) {
        List<CartItem> cartItems = cartItemRepository.findAllById(cartItemIds);
        return cartItems.stream()
                .map(item -> CartItemDto.builder()
                        .id(item.getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .totalPrice(item.getPrice() * item.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }


    public List<CartItemDto> getCartItems(Long id) {
        // 멤버이름으로 find
        ShoppingCart cart = cartRepository.findByMemberId(id)
                .orElseThrow(() -> new IllegalArgumentException("장바구니를 찾을 수 없습니다."));
        log.info("Cart = {} ", cartRepository.findByMemberId(id));

        return cart.getCartItems().stream()
                .map(item -> CartItemDto.builder()
                        .id(item.getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .totalPrice(item.getPrice() * item.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }

    public void deleteCartItem(Long cartItemId) {
        log.info("what is id = {}",cartItemId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("상품음슴"));
        log.info("cartItem = {}", cartItem);
        cartItemRepository.delete(cartItem);
    }
}
