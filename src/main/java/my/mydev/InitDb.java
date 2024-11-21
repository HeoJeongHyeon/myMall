package my.mydev;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import my.mydev.domain.Product.domain.Product;
import my.mydev.domain.Product.dto.ProductDto;
import my.mydev.domain.Product.repository.ProductRepository;
import my.mydev.domain.member.domain.Member;
import my.mydev.domain.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;

    @PostConstruct
    public void init() {
        /* 관리자 계정 생성 및 초기 계정 */
        Member member = Member.builder()
                .username("hishop")
                .email("hi@hi")
                .password(passwordEncoder.encode("hihihi"))
                .build();
        memberRepository.save(member);

        // 관리자 계정 생성
        Member admin = Member.createAdmin(
                "admin",
                "admin@admin",
                passwordEncoder.encode("hihihi")
        );
        memberRepository.save(admin);

        System.out.println("Initial data created");

        Product product = Product.builder()
                .name("chokosong2")
                .description(" eat. The Best snack.")
                .price(1000)
                .stockQuantity(100)
                .build();
        productRepository.save(product);

        Product product2 = Product.builder()
                .name("kancho")
                .description(" 한 달에 30봉 먹는듯.")
                .price(1000)
                .stockQuantity(100)
                .build();
        productRepository.save(product2);

        Product product3 = Product.builder()
                .name("꿀 꽈배기")
                .description(" 헬스후 기력보충용 ")
                .price(1000)
                .stockQuantity(100)
                .build();
        productRepository.save(product3);
    }
}
