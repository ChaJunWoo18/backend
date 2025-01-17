package cha.friendly.controller.form;

import cha.friendly.domain.enumP.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class MemberForm {
    @NotBlank(message = "모두 입력해야합니다.")
    private String name; //nickname . 중복 불가
    private String email; //계정 id . 중복 불가
    private String password;
    private String phoneNumber; //1인 1번호. 중복 불가
    private Role role; // COUNSELOR, NONCOUNSELOR, MENTOR, USER
}
