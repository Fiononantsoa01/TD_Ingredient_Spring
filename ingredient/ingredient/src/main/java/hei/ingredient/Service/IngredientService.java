package hei.ingredient.Service;

import hei.ingredient.Entity.IngredientEntity;
import hei.ingredient.Repository.IngredientRepository;
import hei.ingredient.Validator.IngredientValidator;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository repository;
    private final IngredientValidator validator;
    private final DataSource dataSource;

    public IngredientService(IngredientRepository repository, IngredientValidator validator, DataSource dataSource) {
        this.repository = repository;
        this.validator = validator;
        this.dataSource = dataSource;
    }

    public List<IngredientEntity> getIngredients(int page, int size) {
        validator.validatePagination(page, size);
        return repository.findAll(page, size);
    }

    public IngredientEntity saveIngredient(IngredientEntity ingredient) {
        validator.validate(ingredient);
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            if (repository.existsByName(ingredient.getName())) throw new RuntimeException("Ingredient exists");
            repository.insertIngredient(conn, ingredient);
            conn.commit();
            return ingredient;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}