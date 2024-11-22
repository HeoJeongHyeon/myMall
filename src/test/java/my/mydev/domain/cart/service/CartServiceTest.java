package my.mydev.domain.cart.service;

import my.mydev.domain.Product.domain.Product;
import my.mydev.domain.Product.repository.ProductRepository;
import my.mydev.domain.cart.domain.CartItem;
import my.mydev.domain.cart.domain.ShoppingCart;
import my.mydev.domain.cart.repository.CartItemRepository;
import my.mydev.domain.cart.repository.CartRepository;
import my.mydev.domain.member.domain.Member;
import my.mydev.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private MemberRepository memberRepository;

    private Member testMember;
    private Product testProduct;
    private ShoppingCart testCart;

    @BeforeEach
    void setUp() {
        // 테스트용 멤버 생성
        testMember = testMember.builder()
                .username("tesUser")
                .build();

        // 테스트용 상품 생성
        testProduct = Product.builder()
                .name("테스트 상품")
                .price(10000)
                .stockQuantity(100)
                .description("테스트 상품입니다")
                .build();

        // 테스트용 장바구니 생성
        testCart = new ShoppingCart();
        testCart.setId(1L);
        testCart.setMember(testMember);
        testCart.setCartItems(new ArrayList<>());
    }

    @Test
    @DisplayName("장바구니에 상품 추가 - 새로운 상품")
    void addToCart_NewItem() {
        // given
        Long memberId = 1L;
        Long productId = 1L;
        Integer quantity = 2;

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(testMember));
        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        when(cartRepository.findByMemberId(memberId)).thenReturn(Optional.of(testCart));
        when(cartItemRepository.findByCartIdAndProductId(testCart.getId(), productId))
                .thenReturn(null);

        // when
        cartService.addCart(memberId, productId, quantity);

        // then
        verify(cartItemRepository, times(1)).save(any(CartItem.class));
        verify(cartRepository, times(1)).findByMemberId(memberId);
    }
}