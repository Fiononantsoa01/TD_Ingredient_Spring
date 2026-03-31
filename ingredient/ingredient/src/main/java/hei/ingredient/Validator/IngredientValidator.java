package hei.ingredient.Validator;

import hei.ingredient.Entity.IngredientEntity;
import hei.ingredient.Exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class IngredientValidator {
    public void validatePagination(int page, int size) {
        if (page < 1) throw new IllegalArgumentException("Page must be >= 1");
        if (size < 1) throw new IllegalArgumentException("Size must be >= 1");
    }
    public void validateNewIngredients(List<IngredientEntity> newIngredients) {
        // Vérifie que la liste n'est pas vide
        if (newIngredients == null || newIngredients.isEmpty()) {
            throw new IllegalArgumentException("Ingredient list cannot be empty");
        }

        // Vérifie les doublons dans la nouvelle liste
        Set<String> names = new HashSet<>();
        for (IngredientEntity ing : newIngredients) {
            if (!names.add(ing.getName())) {
                throw new RuntimeException("Duplicate ingredient in input list: " + ing.getName());
            }
        }
    }
    public void validateCriteria(int page, int size) {
        if (page <= 0) {
            throw new BadRequestException("Page must be greater than 0");
        }
        if (size <= 0) {
            throw new BadRequestException("Size must be greater than 0");
        }
    }
}
