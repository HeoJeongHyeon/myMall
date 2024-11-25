package my.mydev.domain.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.mydev.domain.address.dto.AddressDto;
import my.mydev.domain.address.service.AddressService;
import my.mydev.domain.cart.dto.CartItemDto;
import my.mydev.domain.cart.service.CartService;
import my.mydev.domain.member.domain.Member;
import my.mydev.domain.member.repository.MemberRepository;
import my.mydev.domain.member.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/order")
@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final MemberService memberService;
    private final AddressService addressService;
    private final CartService cartService;
    private final MemberRepository memberRepository;

    @GetMapping("/form")
    public String orderForm(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam(value="cartItemId",required = false) List<Long> cartItemIds,
                            @RequestParam(value="addressId",required = false) Long addressId,
                            Model model) {
        /*주문 결제 폼을 뿌려야함.*/
        /* 멤버id와 연관된 cart의 list를 다 가져와서 매핑.
         * 주소 입력창에 Address도 저장해야함.
         * 결제하기 누르면 완료되고, 내 주문 내역을 조회 */
        try{
            Member member = memberService.findByEmail(userDetails.getUsername());
            log.info("memberId = {}", member.getId());
            // 저장된 주소 목록
            List<AddressDto> addresses = addressService.getAddressByMember(member);
            log.info("addresses = {}", addresses);
            model.addAttribute("addresses", addresses);

            /**/
            if (addressId != null) {
                model.addAttribute("addressId", addressId);
            }
            // 장바구니 아이템이 있는 경우에만 처리
            if (cartItemIds != null && !cartItemIds.isEmpty()) {
                List<CartItemDto> cartItems = cartService.getCartItems(member.getId());
                int totalAmount = cartItems.stream()
                        .mapToInt(CartItemDto::getTotalPrice)
                        .sum();


                model.addAttribute("cartItemsIds", cartItemIds);
                model.addAttribute("totalAmount", totalAmount);
                model.addAttribute("cartItems", cartItems);
                /**/
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
                            @RequestParam("cartItemId") List<Long> cartItemIds,
                            @RequestParam("addressId") Long addressId) {
        return "redirect:/cart/cartview";
    }

    @GetMapping("/direct")
    public String orderDirect() {

        /* 바로 주문하기 창으로 이동해야함.
         * 현재 해당 ProductId를 가져와서 주문입력 폼에 매핑
         * */
        return "form";
    }
}
