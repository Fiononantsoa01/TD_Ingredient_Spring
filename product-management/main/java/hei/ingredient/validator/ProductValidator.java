package hei.ingredient.validator;

import hei.ingredient.exception.BadRequestException;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ProductValidator {
    public void validatePagination(Integer page, Integer size) {
        if (page == null || size == null) {
            throw new BadRequestException("page/size must not be null");
        }

        if (page <= 0) {
            throw new BadRequestException("page must be > 0");
        }

        if (size <= 0) {
            throw new BadRequestException("size must be > 0");
        }
    }
    public void validateCriteria(Instant min, Instant max) {
        if (min != null && max != null && min.isAfter(max)) {
            throw new BadRequestException("creationMin must be before creationMax");
        }
    }
    public void validateProduct(String name, Double price) {
        if (name == null || name.isBlank()) {
            throw new BadRequestException("name is required");
        }

        if (price == null) {
            throw new BadRequestException("price must not be null");
        }

        if (price < 0) {
            throw new BadRequestException("price must be positive");
        }
    }
    public void validateSearchCriteria(String productName, String categoryName) {
        if (productName != null && productName.length() < 2)
            throw new BadRequestException("Product name must be at least 2 characters");
        if (categoryName != null && categoryName.length() < 2)
            throw new BadRequestException("Product category name must be at least 2 characters");
    }
}
