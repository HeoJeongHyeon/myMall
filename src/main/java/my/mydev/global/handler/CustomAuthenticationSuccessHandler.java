package my.mydev.global.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        log.info("유저 권한: {}", authorities);
        // 권한에 따른 리다이렉트 처리
        if (authentication.getAuthorities().toString().contains("ROLE_ADMIN")) {
            log.info("관리자 유저 /admin");
            response.sendRedirect("/admin/dashboard");
        } else {
            log.info("일반유저 /home");
            response.sendRedirect("/home");
        }
    }

}
