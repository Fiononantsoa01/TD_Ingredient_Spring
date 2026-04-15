package hei.ingredient.Service;

import hei.ingredient.Entity.DishEntity;
import hei.ingredient.Entity.DishIngredientEntity;
import hei.ingredient.Entity.IngredientEntity;
import hei.ingredient.Repository.DishRepository;
import hei.ingredient.Validator.DishValidator;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public List<DishEntity> findDishesByIngredientName(String name) {
        return repository.findDishesByIngredientName(name);
    }
   /*public DishEntity saveDish(DishEntity dishToSave) {*/
   /* public List<DishIngredientEntity>saveDishIngredient(List<DishIngredientEntity> dishIngredient) {
    List<DishIngredientEntity> dishIngredientEntities = new ArrayList<>();
       /* validator.validate(dishToSave);
        try (Connection conn = dataSource.getConnection()) {

            conn.setAutoCommit(false);
           /* DishEntity existingDish = repository.findByName(dishToSave.getName());
            if (existingDish == null) {
                // ✅ Nouveau dish
                int generatedId = repository.insert(conn, dishIngredient);
                dishToSave.setId(generatedId);

            }
               else {
                    // ⚠️ Dish existe déjà
                    // On garde ID et NAME existants
                    dishToSave.setId(existingDish.getId());
                    dishToSave.setName(existingDish.getName());
            }
            repository.updateDishIngredients(conn, dishToSave);

            conn.commit();
            return dishIngredientEntities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
   public void saveDish(Integer dishId, List<DishIngredientEntity> list) {

       if (dishId == null) {
           throw new IllegalArgumentException("dishId obligatoire");
       }

       if (list == null) {
           throw new IllegalArgumentException("Liste vide");
       }

       // optionnel mais recommandé
       if (!repository.existsById(dishId)) {
           throw new RuntimeException("Dish introuvable");
       }

       repository.updateDishIngredients(dishId, list);
   }


}
