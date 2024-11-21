package my.mydev.domain.Product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import my.mydev.domain.Product.domain.Product;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {

    private Long id;

    @NotBlank(message = "상품명은 필수입니다")
    private String name;

    @NotBlank(message = "상품 설명은 필수입니다.")
    private String description;

    @NotNull(message = "가격은 필수입니다.")
    @Min(value=0,message = "가격은 0보다 커야 합니다.")
    private int price;

    @NotNull(message = "재고 수량은 필수입니다.")
    @Min(value=0,message = "재고 수량은 0보다 커야 합니다.")
    private int StockQuantity;

    /*@NotNull(message = "카테고리는 필수입니다.")
    private Long categoryId;*/

    //  Entity -> DTO
    public static ProductDto from(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setStockQuantity(product.getStockQuantity());
        return productDto;
    }
}
