package my.mydev.domain.Product.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.mydev.domain.Product.domain.Product;
import my.mydev.domain.Product.dto.ProductDto;
import my.mydev.domain.Product.repository.ProductRepository;
import my.mydev.domain.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    /* 유저가 상품보기클릭시 모두 조회함.*/
    public List<ProductDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }

    /* 페이징 처리 수정중 */
    public Page<ProductDto> getProductPage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productRepository.findAll(pageRequest)
                .map(ProductDto::from);
    }

    /* 유저가 상품클릭시 상세 조회 */
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("상품 없음"));
        return ProductDto.from(product);
    }

    /* 관리자 */

    /* 관리자가 상품을 추가하는 기능 구현 */
    public ProductDto addProduct(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .stockQuantity(productDto.getStockQuantity())
                .build();
                log.info("productDto: {}, {}, {}, {},", productDto.getName(),productDto.getDescription(),productDto.getPrice(),productDto.getStockQuantity());
        return ProductDto.from(productRepository.save(product));
    }

    /* 관리자 상품 수정 */
    public void updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> (new EntityNotFoundException("상품 없음")));

        product.update(
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getStockQuantity()
        );

    }

    /* 관리자 상품 삭제 */
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> (new EntityNotFoundException("상품없음")));

        productRepository.delete(product);
    }





    /* 관리자 재고 확인*/

    /* 관리자 재고 증가 */

    /* 관리자 재고 감소 */

}
