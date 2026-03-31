package hei.ingredient.Service;

import hei.ingredient.Entity.IngredientEntity;
import hei.ingredient.Repository.IngredientRepository;
import hei.ingredient.Validator.IngredientValidator;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository repository;
    private final IngredientValidator validator;
    private final DataSource dataSource;

    public IngredientService(IngredientRepository repository, IngredientValidator validator,DataSource dataSource) {
        this.repository = repository;
        this.validator = validator;
        this.dataSource = dataSource;
    }

    public List<IngredientEntity> getIngredients(int page, int size) {
        validator.validatePagination(page, size);
        return repository.findIngredients(page, size);
    }

    public List<IngredientEntity> createIngredients(List<IngredientEntity> newIngredients) {

        if (newIngredients == null || newIngredients.isEmpty()) {
            return List.of();
        }

        List<IngredientEntity> createdIngredients = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            conn.setAutoCommit(false);

            for (IngredientEntity ingredient : newIngredients) {
                if (repository.existsByName( ingredient.getName())) {
                    throw new RuntimeException("Ingredient already exists: " + ingredient.getName());
                }

                // ✅ insert
                IngredientEntity created = repository.insertIngredient( ingredient);
                createdIngredients.add(created);
            }

            conn.commit();
            return createdIngredients;

        } catch (Exception e) {
            throw new RuntimeException("Error in createIngredients: " + e.getMessage(), e);
        }
    }
}
