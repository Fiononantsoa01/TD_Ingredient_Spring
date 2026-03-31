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

    public DishService(DishRepository repository, DataSource dataSource, DishValidator validator) {
        this.repository = repository;
        this.validator = validator;
        this.dataSource = dataSource;
    }

    public DishEntity getDishById(Integer id) {
        validator.validateId(id);
        return repository.findDishById(id);
    }

    public DishEntity saveDish(DishEntity dish) {
        validator.validate(dish);
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            boolean exists = dish.getId() != null && repository.existsById(dish.getId());

            if (!exists) repository.insert(conn, dish);
            else repository.update(conn, dish);

            conn.commit();
            return dish;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}