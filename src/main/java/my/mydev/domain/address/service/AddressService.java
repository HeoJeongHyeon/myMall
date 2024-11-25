package my.mydev.domain.address.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.mydev.domain.address.domain.Address;
import my.mydev.domain.address.dto.AddressDto;
import my.mydev.domain.address.repository.AddressRepository;
import my.mydev.domain.member.domain.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressDto saveAddress(Member member, AddressDto addressDto) {
        Address address = Address.builder()
                .zipCode(addressDto.getZipCode())
                .fullAddress(addressDto.getFullAddress())
                .addressDetail(addressDto.getAddressDetail())
                .member(member)
                .build();
        log.info("Saving address: {}", address);

        Address savedAddress = addressRepository.save(address);
        return AddressDto.from(savedAddress);
    }

    public AddressDto getAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("주소 없음."));

        return AddressDto.from(address);
    }


    public List<AddressDto> getAddressByMember(Member member) {
        List<Address> addresses = addressRepository.findByMember(member);
        return addresses.stream()
                .map(AddressDto::from)
                .collect(Collectors.toList());

    }
}
