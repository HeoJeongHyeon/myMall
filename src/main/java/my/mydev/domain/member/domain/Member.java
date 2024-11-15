package my.mydev.domain.member.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.mydev.domain.address.domain.Address;
import my.mydev.domain.order.domain.Order;

import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

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
    @Size(min=6)
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Order> orders;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Address> address;

    @Builder
    public Member(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.orders = new ArrayList<>();
        this.address = new ArrayList<>(); // npe 방지

    }

}