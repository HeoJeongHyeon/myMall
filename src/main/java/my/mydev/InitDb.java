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

        Product product4 = Product.builder()
                .name("아메리카노")
                .description("아침을 깨워주는 상쾌한 커피")
                .price(3000)
                .stockQuantity(200)
                .build();

        Product product5 = Product.builder()
                .name("프로틴 바")
                .description("운동 후 근육 회복을 돕는 단백질 바")
                .price(2500)
                .stockQuantity(150)
                .build();

        Product product6 = Product.builder()
                .name("초코칩 쿠키")
                .description("달콤한 초콜릿이 들어간 쿠키")
                .price(1500)
                .stockQuantity(120)
                .build();

        Product product7 = Product.builder()
                .name("생수")
                .description("순수한 물, 갈증 해소에 최고")
                .price(500)
                .stockQuantity(300)
                .build();

        Product product8 = Product.builder()
                .name("샌드위치")
                .description("든든한 한 끼 대용 샌드위치")
                .price(4000)
                .stockQuantity(80)
                .build();

        Product product9 = Product.builder()
                .name("요거트")
                .description("신선한 과일이 들어간 요거트")
                .price(2000)
                .stockQuantity(50)
                .build();

        Product product10 = Product.builder()
                .name("햄버거")
                .description("직화로 구운 패티가 들어간 햄버거")
                .price(5000)
                .stockQuantity(60)
                .build();

        Product product11 = Product.builder()
                .name("감자튀김")
                .description("바삭바삭한 감자튀김")
                .price(1500)
                .stockQuantity(110)
                .build();

        Product product12 = Product.builder()
                .name("콜라")
                .description("청량감을 느낄 수 있는 탄산음료")
                .price(1500)
                .stockQuantity(250)
                .build();

// 상품 저장
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(product6);
        productRepository.save(product7);
        productRepository.save(product8);
        productRepository.save(product9);
        productRepository.save(product10);
        productRepository.save(product11);
        productRepository.save(product12);

    }
}
