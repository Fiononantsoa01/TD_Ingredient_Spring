package hei.ingredient.Validator;

import hei.ingredient.Entity.DishEntity;
import hei.ingredient.Exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class DishValidator {
    public void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Invalid id: " + id);
        }
    }
    public void validate(DishEntity dish) {
        if (dish == null) {
            throw new BadRequestException("Dish cannot be null");
        }

        if (dish.getName() == null || dish.getName().isBlank()) {
            throw new BadRequestException("Dish name is required");
        }

        if (dish.getDishType() == null) {
            throw new BadRequestException("Dish type is required");
        }
    }
}
