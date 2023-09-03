package cha.friendly.controller;

import cha.friendly.controller.MemberForm;
import cha.friendly.domain.Member;
import cha.friendly.domain.Role;
import cha.friendly.service.LoginService;
import cha.friendly.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService; //주로 controller가 service를 갖다 씀

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @ModelAttribute("roles")
    public Role[] roles() {
        return Role.values(); // 해당 ENUM의 모든 정보를 배열로 반환한다.
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form,//valid를 하면 MemberForm 클래스의 notEmpty를 적용시킨다.
                         BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Member member = new Member();
        member.setName(form.getName());
        member.setEmail(form.getEmail());
        member.setPassword(form.getPassword());
        member.setPhoneNumber(form.getPhoneNumber());
        member.setRole(form.getRole());

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}