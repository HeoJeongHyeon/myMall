package my.mydev.domain.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.mydev.domain.address.dto.AddressDto;
import my.mydev.domain.address.service.AddressService;
import my.mydev.domain.cart.domain.CartItem;
import my.mydev.domain.cart.dto.CartItemDto;
import my.mydev.domain.cart.service.CartService;
import my.mydev.domain.member.domain.Member;
import my.mydev.domain.member.repository.MemberRepository;
import my.mydev.domain.member.service.MemberService;
import my.mydev.domain.order.dto.OrderDto;
import my.mydev.domain.order.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/order")
@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final MemberService memberService;
    private final AddressService addressService;
    private final CartService cartService;
    private final OrderService orderService;
    @GetMapping("/form")
    public String orderForm(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam(value="cartItemId",required = false) String cartItemIds,
                            @RequestParam(value="addressId",required = false) Long addressId,
                            Model model) {

        try {
            Member member = memberService.findByEmail(userDetails.getUsername());
            log.info("memberId = {}", member.getId());
            // 저장된 주소 목록
            List<AddressDto> addresses = addressService.getAddressByMember(member);
            log.info("addresses = {}", addresses);
            model.addAttribute("addresses", addresses);


            if (addressId != null) {
                model.addAttribute("addressId", addressId);
            }
            // 장바구니 아이템이 있는 경우에만 처리
            if (cartItemIds != null && !cartItemIds.isEmpty()) {
                List<CartItemDto> cartItems = cartService.getCartItems(member.getId());
                log.info("cartItems = {}", cartItems.toArray());
                int totalAmount = cartItems.stream()
                        .mapToInt(CartItemDto::getTotalPrice)
                        .sum();


                model.addAttribute("cartItemsIds", cartItemIds);
                model.addAttribute("totalAmount", totalAmount);
                model.addAttribute("cartItems", cartItems);

            }
        } catch (Exception e) {
            log.error("주문폼 생성 오류",e);
            return "redirect:/cart/cartview";
        }
        log.info("여기까지");
        return "order/form";
    }

    @PostMapping("/form")
    public String orderForm(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam("cartItemIds") String cartItemIds,
                            @RequestParam("addressId") Long addressId,
                            RedirectAttributes redirectAttributes) {
        try {
            String username = userDetails.getUsername();
            Member member = memberService.findByEmail(username);
            log.info("@@@@@@@ 아이디 memberId = {}", member.getId());
            log.info("@@@@@@@ 아이디 cartItemId = {}", cartItemIds);
            List<Long> cartItemIdList = Arrays.stream(cartItemIds.split(","))
                    .map(String::trim)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            // 변환된 리스트가 비어있는지 체크
            if (cartItemIdList.isEmpty()) {
                throw new IllegalArgumentException("유효한 장바구니 아이템이 없습니다.");
            }
            Long orderId = orderService.createOrder(member.getId(), cartItemIdList, addressId);
            redirectAttributes.addFlashAttribute("successMessage", "주문 성공");

        } catch (Exception e) {
            log.error("주문 처리 중 오류 발생", e);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/cart/cartview";
        }

        return "redirect:/order/orderview";
    }

    @GetMapping("/orderview")
    public String orderView(@AuthenticationPrincipal UserDetails userDetails,Model model) {
        try {
            Member member = memberService.findByEmail(userDetails.getUsername());
            List<OrderDto> orders = orderService.getOrdersByMemberId(member.getId());
            model.addAttribute("orders", orders);
            return "order/orderview";
        } catch (Exception e) {
            log.error("주문 내역 조회 중 오류 발생", e);
            return "redirect:/";
        }

    }
}
