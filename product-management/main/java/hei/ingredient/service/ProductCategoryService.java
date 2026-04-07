package hei.ingredient.service;

import hei.ingredient.entity.ProductCategory;
import hei.ingredient.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository repository;
    public ProductCategoryService(ProductCategoryRepository repository) {
        this.repository = repository;
    }
    public List<ProductCategory> getAllCategories() {
        return repository.findAllCategory();
    }
    public ProductCategory getCategoryByProductId(Integer id) {
        return  repository.findCategoryByProductId( id);
    }

}
