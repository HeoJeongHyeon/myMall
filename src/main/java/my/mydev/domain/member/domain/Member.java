package my.mydev.domain.member.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import my.mydev.domain.address.domain.Address;
import my.mydev.domain.order.domain.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 2, max = 50)
    private String username;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 6)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Order> orders;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Address> address;

    @Builder
    public Member(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
        this.orders = new ArrayList<>();
        this.address = new ArrayList<>(); // npe 방지

    }

    /* 관리자 테스트동안 사용하려고 임시 생성 마지막에 코드 정리할 때 에드민 생성용 하나 만들어야함.*/
    // ADMIN용 정적 팩토리 메서드
    public static Member createAdmin(String username, String email, String password) {
        Member admin = new Member();
        admin.username = username;
        admin.email = email;
        admin.password = password;
        admin.role = Role.ADMIN;
        admin.orders = new ArrayList<>();
        admin.address = new ArrayList<>();
        return admin;
    }


}
