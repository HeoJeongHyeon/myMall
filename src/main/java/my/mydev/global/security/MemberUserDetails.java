package my.mydev.global.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import my.mydev.domain.member.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
//@RequiredArgsConstructor
public class MemberUserDetails implements UserDetails, Serializable {

//    private final Member member;
    // 직렬화
    private static final long serialVersionUID = 1L;
    private final String username;
    private final String password;
    private final String name;
    private final Collection<? extends GrantedAuthority> authorities;

    // 직렬화 필요해서 일단 임시로 해둠.
    public MemberUserDetails(Member member) {
        this.username = member.getEmail();
        this.password = member.getPassword();
        this.name = member.getUsername();
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+member.getRole()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
//        return Collections.singletonList(
//                new SimpleGrantedAuthority("ROLE_" + member.getRole())
//        );
    }

    @Override
    public String getPassword() {
//        return member.getPassword();
        return password;
    }

    @Override
    public String getUsername() {
//        return member.getEmail();
        return username;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;    // 만료되지 않음
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;    // 만료되지 않음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;    // 만료되지 않음
    }

    @Override
    public boolean isEnabled() {
        return true;    // 만료되지 않음
    }
}
