package my.mydev.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.mydev.domain.member.dto.MemberDto;
import my.mydev.domain.member.service.MemberService;
import my.mydev.global.security.MemberUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute MemberDto memberDto, BindingResult result) {
        if (result.hasErrors()) {
            return "signup";
        }

        try {
            memberService.saveMember(memberDto);
            return "redirect:/login";
        } catch (IllegalStateException e) {
            result.rejectValue("email", "error.memberDto", e.getMessage());
            return "signup";
        }
    }

    @GetMapping("/login")
    public String loginForm(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model
    ) {
        if (error != null) {
            model.addAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        if (logout != null) {
            model.addAttribute("message", "로그아웃 되었습니다.");
        }
        return "login";
    }

    @GetMapping("/home")
    public String memberHome(@AuthenticationPrincipal MemberUserDetails userDetails, Model model) {
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getName());
            model.addAttribute("email", userDetails.getUsername());
        }
//        model.addAttribute("member", userDetails.getMember());
        return "home";
    }
}