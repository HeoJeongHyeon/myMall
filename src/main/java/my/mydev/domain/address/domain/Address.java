package my.mydev.domain.address.domain;

import jakarta.persistence.*;
import lombok.*;
import my.mydev.domain.member.domain.Member;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude="member")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    // 전체로 적는걸로 일단, (예: 경기도 고양시 일산서구식으로 )
    private String fullAddress;
    private String zipCode;
    private String addressDetail;
    @Builder
    public Address(Member member, String fullAddress, String zipCode, String addressDetail) {
        this.member = member;
        this.fullAddress = fullAddress;
        this.zipCode = zipCode;
        this.addressDetail = addressDetail;
    }
}
