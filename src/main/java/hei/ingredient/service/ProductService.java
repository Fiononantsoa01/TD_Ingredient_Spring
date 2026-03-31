package hei.ingredient.service;

import hei.ingredient.entity.Product;
import hei.ingredient.exception.NotFoundException;
import hei.ingredient.repository.ProductRepository;
import hei.ingredient.validator.ProductValidator;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
@Service
public class ProductService {
    private final ProductRepository repository;
    private final ProductValidator validator;
    public ProductService(ProductRepository repository, ProductValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }
    public List<Product> getAllProductWithPagination(int page, int size) {
        return repository.getProductList(page, size);
    }
    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationMin,
            Instant creationMax
    ) {

        validator.validateCriteria(creationMin, creationMax);

        List<Product> products = repository.getProductByCriteria(
                productName,
                categoryName,
                creationMin,
                creationMax
        );

        if (products.isEmpty()) {
            throw new NotFoundException("Product", "criteria");
        }
        return products;
    }

}
