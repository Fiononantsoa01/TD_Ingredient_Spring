package hei.ingredient.Validator;

import org.springframework.stereotype.Component;

@Component
public class IngredientValidator {
    public void validatePagination(int page, int size) {
        if (page < 1) throw new IllegalArgumentException("Page must be >= 1");
        if (size < 1) throw new IllegalArgumentException("Size must be >= 1");
    }
}
