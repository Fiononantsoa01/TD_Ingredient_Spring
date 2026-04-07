package hei.ingredient.controller;

import hei.ingredient.entity.ProductCategory;
import hei.ingredient.service.ProductCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class ProductCategoryController {
    private final ProductCategoryService service;
    public ProductCategoryController(ProductCategoryService service) {
        this.service = service;
    }
    @GetMapping("/api/categories")
    public List<ProductCategory> getAllCategories() {
        return service.getAllCategories();
    }
    @GetMapping("/api/categories/byProduct/{proId}")
    public ProductCategory getCategoryByProductId(@PathVariable Integer ProductId) {
        return service.getCategoryByProductId(ProductId);
    }
}
