package hei.ingredient.validator;

import hei.ingredient.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {
    public void validatePagination(int page, int size) {
        if(page<1) throw new IllegalArgumentException("page must be greater than 0");
        if(size<1) throw new IllegalArgumentException("size must be greater than 0");
    }
    public void validateSearchCriteria(String productName, String categoryName) {
        if (productName != null && productName.length() < 2)
            throw new BadRequestException("Product name must be at least 2 characters");
        if (categoryName != null && categoryName.length() < 2)
            throw new BadRequestException("Product category name must be at least 2 characters");
    }
}
