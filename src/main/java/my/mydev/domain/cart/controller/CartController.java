package my.mydev.domain.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.mydev.domain.cart.dto.CartItemDto;
import my.mydev.domain.cart.service.CartService;
import my.mydev.domain.member.domain.Member;
import my.mydev.domain.member.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
@Slf4j
public class CartController {

    private final CartService cartService;
    private final MemberService memberService;

    @PostMapping("/add")
    public String addCart(@AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam("productId") Long productId,
                          @RequestParam("quantity") Integer stockQuantity,
                          RedirectAttributes redirectAttributes) {
        try {
            log.info("상품 장바구니 시작1");
            log.info("userDetails email: {}", userDetails.getUsername());
            Member member = memberService.findByEmail(userDetails.getUsername());
            log.info("찾은 member 정보: {}", member.getEmail());
            log.info("상품 장바구니 시작2");
            cartService.addCart(member.getId(), productId, stockQuantity);
            log.info("상품 장바구니 시작3");
            redirectAttributes.addFlashAttribute("message", "상품 장바구니에 추가 ");

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            log.info("상품 장바구니 오류 발생");
        }
        return "redirect:/products/list"; // 상품 목록 페이지로 리다이렉트
    }

    /* Cart 보기 */
    @GetMapping("/cartview")
    public String viewCart(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        /* 유저 검증후 해당 Member 객체를 통해 카트 조회, 아이템 조회 */
        try {
            Member member = memberService.findByEmail(userDetails.getUsername());
            List<CartItemDto> cartItems = cartService.getCartItems(member.getId());
            int totalAmount = cartItems.stream()
                    .mapToInt(CartItemDto::getTotalPrice)
                    .sum();

            model.addAttribute("cartItems", cartItems);
            model.addAttribute("totalAmount", totalAmount);
        } catch (Exception e) {
            model.addAttribute("error", "장바구니를 불러오는 중 오류가 발생");
        }
        return "cart/cartview";
    }

    /* Cart 삭제 */

}
