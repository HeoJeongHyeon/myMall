package my.mydev.domain.address.repository;

import my.mydev.domain.address.domain.Address;
import my.mydev.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {

    List<Address> findByMember(Member member);
}
