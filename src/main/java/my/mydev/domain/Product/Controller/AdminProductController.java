package my.mydev.domain.Product.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.mydev.domain.Product.dto.ProductDto;
import my.mydev.domain.Product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminProductController {

    private final ProductService productService;

    // 관리자 대시보드
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<ProductDto> product = productService.findAll();
        model.addAttribute("products", product);
        log.info("Found {} products", product.size());  // 로그 추가
        return "admin/dashboard";
    }

    // 상품 목록
    @GetMapping("/products/list")
    public String list(Model model) {
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin/products/list";
    }


    /* 상품 추가 */
    /* /admin/products/add*/
    @GetMapping("/products/addProduct")
    public String addProduct(Model model) {
        model.addAttribute("productDTO", new ProductDto());
        return "admin/products/addProduct";
    }

    /* 상품 추가 */
    /* /admin/products/add*/
    @PostMapping("/products/addProduct")
    public String addProduct(@Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/products/addProduct";
        }
        productService.addProduct(productDto);
        return "redirect:/admin/dashboard"; /* 상품 업로드시 바로 확인할 수 있게.*/
    }

    /* 상품 수정 */
    /* /admin/products/{id}/edit*/
    @GetMapping("/products/{id}/editProduct")
    public String editProduct(@PathVariable (name = "id")Long id, Model model) {
        ProductDto productdto = productService.findById(id);
        model.addAttribute("productDto", productdto);
        log.info("GET 접속  {}", productdto);
        return "admin/products/editProduct";
    }

    /* 상품 수정 */
    /* /admin/products/{id}/edit*/
    @PostMapping("/products/{id}/editProduct")
    public String editProduct(@PathVariable(name="id")Long id, @Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult bindingResult,
    RedirectAttributes redirectAttributes ) {
        if (bindingResult.hasErrors()) {
            return "admin/products/editProduct";
        }

        try {
            productService.updateProduct(id, productDto);
            log.info("Post {},{},{},{},{}", productDto.getName(),productDto.getPrice(),productDto.getId(),productDto.getStockQuantity(),productDto.getDescription());
            redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 수정되었습니다.");
            return "redirect:/admin/dashboard";  // 대시보드로 리다이렉트
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "상품 수정 중 오류가 발생했습니다.");
            return "redirect:/admin/dashboard";
        }
    }

    /* 상품 삭제 */
    /* admin/products/delete */
    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable(name="id") Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("message","상품 성공삭제");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "상품 삭제 중 오류");
        }
        return "redirect:/admin/dashboard";
    }
    /* 재고 감소 */
    /* 재고 증가 */
    /* 재고 확인 */ // 하다보니 계속 많아지네..

}
