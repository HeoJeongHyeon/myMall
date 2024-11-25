package my.mydev.domain.address.dto;

import lombok.*;
import my.mydev.domain.address.domain.Address;
import my.mydev.domain.member.domain.Member;

import java.io.Serializable;


@Getter
@Setter  // Setter 추가
@ToString // ToString 추가
@NoArgsConstructor(access = AccessLevel.PUBLIC) // PUBLIC으로 변경
public class AddressDto implements Serializable {
    private static final Long serialVersionUID = 1L;

    private Long id;
    private Long memberId;
    private String fullAddress;
    private String zipCode;  // ZipCode -> zipCode로 수정
    private String addressDetail;

    @Builder
    public AddressDto(Long id, Long memberId, String fullAddress, String zipCode, String addressDetail) {
        this.id = id;
        this.memberId = memberId;
        this.fullAddress = fullAddress;
        this.zipCode = zipCode;
        this.addressDetail = addressDetail;
    }

    public static AddressDto from(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .memberId(address.getMember().getId())
                .fullAddress(address.getFullAddress())
                .zipCode(address.getZipCode())
                .addressDetail(address.getAddressDetail())
                .build();
    }
}
