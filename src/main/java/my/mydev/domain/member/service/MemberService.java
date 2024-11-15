package my.mydev.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import my.mydev.domain.member.domain.Member;
import my.mydev.domain.member.dto.MemberDto;
import my.mydev.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(MemberDto memberDto) {
        validateDuplicateMember(memberDto);

        Member member = memberDto.toEntity(memberDto);
        return memberRepository.save(member);
    }

    @Transactional
    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("해당 이메일이 존재하지 않습니다."));
        
        if (!member.getPassword().equals(password)) {
            throw new IllegalStateException("잘못된 비밀번호입니다.");
        }
        return member;
    }

    private void validateDuplicateMember(MemberDto memberDto) {
        if (memberRepository.existsByEmail(memberDto.getEmail())) {
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }
    }
}
