package com.example.racekatteklubbendheisino.presentation;

import com.example.racekatteklubbendheisino.application.MemberService;
import com.example.racekatteklubbendheisino.domain.Member;
import org.springframework.stereotype.Controller;
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
        return "login";
    }

    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        Member member = new Member(name, email, password);
        boolean isRegistered = memberService.register(member);
        if (isRegistered) {
            return "redirect:/login";
        } else {
            return "register";
        }
    }
}
