package hei.ingredient.Validator;

import org.springframework.stereotype.Component;

@Component
public class DishValidator {
    public void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Invalid id: " + id);
        }
    }
}
