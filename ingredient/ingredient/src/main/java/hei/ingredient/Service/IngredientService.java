package hei.ingredient.Service;

import hei.ingredient.Entity.IngredientEntity;
import hei.ingredient.Repository.IngredientRepository;
import hei.ingredient.Validator.IngredientValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository repository;
    private final IngredientValidator validator;

    public IngredientService(IngredientRepository repository, IngredientValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public List<IngredientEntity> getIngredients(int page, int size) {
        validator.validatePagination(page, size);
        return repository.findIngredients(page, size);
    }
}
