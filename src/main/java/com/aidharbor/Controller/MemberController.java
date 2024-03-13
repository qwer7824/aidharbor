package com.aidharbor.Controller;

import com.aidharbor.DTO.Member.MemberDTO;
import com.aidharbor.Service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/signup")
    public String memberSign(@Valid MemberDTO memberDTO){
        memberService.join(memberDTO);
        return "member/sign";
    }
    @GetMapping("/member/signup")
    public String memberSignView(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "member/sign";
    }
    @GetMapping("/member/login")
    public String loginPage() {
        return "member/login";
    }
}
