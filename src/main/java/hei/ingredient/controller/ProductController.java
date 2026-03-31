package hei.ingredient.controller;

import hei.ingredient.entity.Product;
import hei.ingredient.entity.ProductCategory;
import hei.ingredient.service.ProductService;
import hei.ingredient.validator.ProductValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService service;
    private final ProductValidator validator;
    public ProductController(ProductService service, ProductValidator validator) {
        this.validator = validator;
        this.service = service;
    }
    @GetMapping("/products")
    public ResponseEntity< List<Product>>getProductsWithPagination(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(
                service.getAllProductWithPagination(page, size)
        );
    }

}
