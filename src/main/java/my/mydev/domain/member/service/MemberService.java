package my.mydev.domain.member.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import my.mydev.domain.member.domain.Member;
import my.mydev.domain.member.dto.MemberDto;
import my.mydev.domain.member.repository.MemberRepository;
import my.mydev.global.security.MemberUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;



    @Transactional
    public Member saveMember(MemberDto memberDto) {
        validateDuplicateMember(memberDto);

        Member member = Member.builder()
                .username(memberDto.getUsername())
                .email(memberDto.getEmail())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .build();
        return memberRepository.save(member);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일 없음"));

        return new MemberUserDetails(member);

    }

    private void validateDuplicateMember(MemberDto memberDto) {
        if (memberRepository.existsByEmail(memberDto.getEmail())) {
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }
    }
}
