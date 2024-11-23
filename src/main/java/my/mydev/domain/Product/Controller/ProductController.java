package my.mydev.domain.Product.Controller;

import lombok.RequiredArgsConstructor;
import my.mydev.domain.Product.domain.Product;
import my.mydev.domain.Product.dto.ProductDto;
import my.mydev.domain.Product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    /* 구현 필요 목록
     * 상품 목록
     * 상품 상세
     * 카테고리별 상품
     * */
    /*@GetMapping("/list")
    public String list(Model model) {
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "products/list";
    }*/

    @GetMapping("/list")
    public String list(@PageableDefault(size = 9) Pageable pageable, Model model) {
        Page<ProductDto> productDtoPage = productService.getProductPage(
                pageable.getPageNumber(),
                pageable.getPageSize()
        );
        model.addAttribute("products", productDtoPage);
        return "products/list";
    }

    @GetMapping("/detail/{id}")
    public String ProductDetail(@PathVariable(name="id") Long id, Model model) {
        ProductDto productDto = productService.findById(id);
        model.addAttribute("productDto", productDto);
        return "products/detail";
    }





}
