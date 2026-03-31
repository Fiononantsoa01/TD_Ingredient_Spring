package hei.ingredient.Validator;

import hei.ingredient.Entity.IngredientEntity;
import hei.ingredient.Exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class IngredientValidator {

    // Validation de la pagination
    public void validatePagination(int page, int size) {
        if (page < 1) throw new BadRequestException("Page must be >= 1");
        if (size < 1) throw new BadRequestException("Size must be >= 1");
    }

    // Validation des nouveaux ingrédients
    public void validateNewIngredients(List<IngredientEntity> newIngredients) {
        if (newIngredients == null || newIngredients.isEmpty()) {
            throw new BadRequestException("Ingredient list cannot be empty");
        }

        // Vérifie les doublons dans la nouvelle liste
        Set<String> names = new HashSet<>();
        for (IngredientEntity ing : newIngredients) {
            if (ing.getName() == null || ing.getName().isBlank()) {
                throw new BadRequestException("Ingredient name cannot be empty");
            }
            if (!names.add(ing.getName())) {
                throw new BadRequestException("Duplicate ingredient in input list: " + ing.getName());
            }
        }
    }

    // Validation des critères de recherche
    public void validateCriteria(int page, int size) {
        if (page <= 0) {
            throw new BadRequestException("Page must be greater than 0");
        }
        if (size <= 0) {
            throw new BadRequestException("Size must be greater than 0");
        }
    }
    public void validate(IngredientEntity ingredient) {
        if (ingredient == null) {
            throw new BadRequestException("Ingredient cannot be null");
        }
        if (ingredient.getName() == null || ingredient.getName().isBlank()) {
            throw new BadRequestException("Ingredient name is required");
        }
        if (ingredient.getPrice() != null && ingredient.getPrice() < 0) {
            throw new BadRequestException("Ingredient price cannot be negative");
        }
        if (ingredient.getCategory() == null) {
            throw new BadRequestException("Ingredient category is required");
        }
    }
}