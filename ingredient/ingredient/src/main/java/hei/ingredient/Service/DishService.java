package hei.ingredient.Service;

import hei.ingredient.Entity.DishEntity;
import hei.ingredient.Repository.DishRepository;
import hei.ingredient.Validator.DishValidator;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class DishService {
    private final DishRepository repository;
    private final DishValidator validator;
    private final DataSource dataSource;
    public DishService(DishRepository repository,DataSource dataSource, DishValidator validator) {
        this.repository = repository;
        this.validator = validator;
        this.dataSource = dataSource;
    }
    public DishEntity getDishById(Integer id) {
        validator.validateId(id);
            DishEntity dish = repository.findDishById(id);
        return dish;
    }
    public DishEntity saveDish(DishEntity dishToSave) {

        validator.validate(dishToSave);
        try (Connection conn = dataSource.getConnection()) {

            conn.setAutoCommit(false);

            boolean exists = dishToSave.getId() != null &&
                    repository.existsById( dishToSave.getId());

            if (!exists) {
                int generatedId = repository.insert(conn, dishToSave);
                dishToSave.setId(generatedId);
            } else {
                repository.update(conn, dishToSave);
            }
            repository.updateDishIngredients(conn, dishToSave);

            conn.commit();
            return dishToSave;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
