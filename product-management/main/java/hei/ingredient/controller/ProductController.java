package hei.ingredient.controller;

import hei.ingredient.entity.Product;
import hei.ingredient.entity.ProductCategory;
import hei.ingredient.service.ProductService;
import hei.ingredient.validator.ProductValidator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
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
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> getProductsBySearchCriteria(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant creationMin,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant creationMax
    ) {
        List <Product> products= service.getProductsByCriteria(
                productName,
                categoryName,
                creationMin,
                creationMax
        );
        return ResponseEntity.ok(products);
    }
    @GetMapping("/products/filter")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String categoryName,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant creationMin,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant creationMax,

            @RequestParam int page,
            @RequestParam int size
    ) {

        return ResponseEntity.ok(
                service.getProductsByCriteria(
                        productName,
                        categoryName,
                        creationMin,
                        creationMax,
                        page,
                        size
                )
        );
    }

}
