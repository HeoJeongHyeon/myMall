package my.mydev.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.mydev.domain.member.dto.MemberDto;
import my.mydev.domain.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/")
    public String home() {
        return "index"; // 기본 화면
    }

    @GetMapping("/signup")
    public String signupForm(Model model) { // Model 통해 데이터 뷰에 전달
        model.addAttribute("memberDto", new MemberDto());
        return "members/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute MemberDto memberDto, BindingResult result) {
        if (result.hasErrors()) {
            return "members/signup";
        }

        try {
            memberService.saveMember(memberDto);
            return "redirect:/members/login";
        } catch (IllegalStateException e) {
            result.rejectValue("email", "error.memberDto", e.getMessage());
            return "members/signup";
        }
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "members/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDto memberDto, BindingResult result, Model model) {
        try {
            memberService.login(memberDto.getEmail(), memberDto.getPassword());/* return member;*/
            return "redirect:/members/"; /* URI 추가 수정 필요 */
        } catch (IllegalStateException e) {
            model.addAttribute("loginError", e.getMessage());
            return "members/login";
        }
    }
}
