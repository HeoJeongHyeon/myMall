package my.mydev.domain.address.domain;

import jakarta.persistence.*;
import my.mydev.domain.member.domain.Member;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    // 전체로 적는걸로 일단, (예: 경기도 고양시 일산서구식으로 )
    private String fullAddress;
    private String ZipCode;

}
