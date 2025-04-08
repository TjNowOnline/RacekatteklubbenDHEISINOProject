package com.example.racekatteklubbendheisino.presentation;

import com.example.racekatteklubbendheisino.application.MemberService;
import com.example.racekatteklubbendheisino.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login"; // Spring Security will handle the POST
    }

    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {
        Member member = new Member(name, email, password);
        boolean isRegistered = memberService.register(member);
        if (isRegistered) {
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Registration failed: Email already in use.");
            return "register";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard.html"; // Create this page or redirect to /pets
    }
}