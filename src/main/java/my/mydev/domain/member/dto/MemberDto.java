package my.mydev.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import my.mydev.domain.member.domain.Member;

@Getter
@Setter
public class MemberDto {


    @NotBlank(message = "사용자 이름은 필수입니다")
    @Size(min = 2, max = 50)
    private String username;

    @NotBlank(message = "이메일은 필수입니다")
    @Email
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(min = 6)
    private String password;

    public Member toEntity(MemberDto memberDto) {
        return Member.builder()
                .username(this.username)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
