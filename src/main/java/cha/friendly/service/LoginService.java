package cha.friendly.service;

import cha.friendly.domain.Member;
import cha.friendly.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * @return null 로그인 실패
     */
    public Member login(String email, String password) {
        return memberRepository.findByLoginId(email)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
