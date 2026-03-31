package hei.ingredient.service;

import hei.ingredient.entity.Product;
import hei.ingredient.repository.ProductRepository;
import hei.ingredient.validator.ProductValidator;
import org.springframework.stereotype.Service;

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
}
