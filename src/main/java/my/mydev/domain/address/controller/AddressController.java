package my.mydev.domain.address.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.mydev.domain.address.dto.AddressDto;
import my.mydev.domain.address.service.AddressService;
import my.mydev.domain.member.domain.Member;
import my.mydev.domain.member.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/address")
@RequiredArgsConstructor
@Slf4j
public class AddressController {

    private final AddressService addressService;
    private final MemberService memberService;

    @GetMapping("/add")
    public String addAddressForm(@ModelAttribute("addressDto") AddressDto addressDto,
                                 @RequestParam(value = "cartItemId", required = false) List<Long> cartItemIds,
                                 Model model) {
        model.addAttribute("cartItemId", cartItemIds);
        return "address/add";
    }

    @PostMapping("/add")
    public String addAddress(@AuthenticationPrincipal UserDetails userDetails,
                             @ModelAttribute AddressDto addressDto,
                             @RequestParam(value = "cartItemId", required = false) List<Long> cartItemIds,
                             RedirectAttributes redirectAttributes) {

        // 데이터 확인을 위한 로깅
        log.info("Received AddressDto: {}", addressDto);
        log.info("CartItemIds: {}", cartItemIds);

        try {
            Member member = memberService.findByEmail(userDetails.getUsername());
            addressDto.setMemberId(member.getId());

            // 저장 전 데이터 확인
            log.info("Saving address for member: {}", member.getEmail());

            AddressDto savedAddress = addressService.saveAddress(member, addressDto);

            // 저장된 주소 데이터 확인
            log.info("Saved address: {}", savedAddress);

            // Flash Attribute로 저장된 주소 정보 전달
            redirectAttributes.addFlashAttribute("addressId", savedAddress.getId());

            // 장바구니 아이템 ID 전달
            if (cartItemIds != null && !cartItemIds.isEmpty()) {
/*
                cartItemIds.forEach(id -> redirectAttributes.addAttribute("cartItemId", id));
*/
                for (Long id : cartItemIds){
                    redirectAttributes.addAttribute("cartItemId", id);
                }
            }

            return "redirect:/order/form?cartItemId="+cartItemIds;

        } catch (Exception e) {
            log.error("Error saving address", e);
            // 에러 메시지를 사용자에게 전달
            redirectAttributes.addFlashAttribute("errorMessage", "배송지 저장 중 오류가 발생했습니다.");
            return "redirect:/address/add";
        }

    }

}
