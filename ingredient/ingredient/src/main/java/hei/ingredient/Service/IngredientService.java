package hei.ingredient.Service;

import hei.ingredient.Entity.Category;
import hei.ingredient.Entity.DishIngredientEntity;
import hei.ingredient.Entity.IngredientEntity;
import hei.ingredient.Entity.StockValue;
import hei.ingredient.Repository.IngredientRepository;
import hei.ingredient.Repository.StockMovementRepository;
import hei.ingredient.Validator.IngredientValidator;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IngredientService {
    private final IngredientRepository repository;
    private final IngredientValidator validator;
    private final DataSource dataSource;
    private final StockMovementRepository stockMovementRepository;

    public IngredientService(IngredientRepository repository, IngredientValidator validator, DataSource dataSource, StockMovementRepository stockMovementRepository) {
        this.repository = repository;
        this.validator = validator;
        this.dataSource = dataSource;
        this.stockMovementRepository = stockMovementRepository;
    }

    public List<IngredientEntity> getIngredients(int page, int size) {
        validator.validatePagination(page, size);
        return repository.findIngredients(page, size);
    }
    public List<IngredientEntity> getAllIngredients() {
        return repository.findAllIngredients();
    }
    public List<DishIngredientEntity> getIngredientByDish(Integer dishId) {
        return repository.findIngredientByDishid(dishId);
    }


    /*public List<IngredientEntity> createIngredients(List<IngredientEntity> newIngredients) {

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
    }*/
    public IngredientEntity saveIngredients(IngredientEntity newIngredients) {
        try(Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            Integer idIngr;
            //check the Ingredient existing
            Integer existingId= repository.findIdByName(conn,newIngredients.getName());
            if(existingId != null) {
                idIngr = existingId;
                newIngredients.setId(idIngr);
            }else{
                idIngr=repository.insertIngredient(conn,newIngredients);
                newIngredients.setId(idIngr);
            }
            newIngredients.setId(idIngr);
            if(newIngredients.getStockMovementList()!=null
            && !newIngredients.getStockMovementList().isEmpty()) {
                stockMovementRepository.insertStockMovements(
                        conn,
                        newIngredients.getStockMovementList(),
                        idIngr
                );
            }
            conn.commit();
            return newIngredients;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public List<IngredientEntity> findIngredientsByCriteria(
            String name,
            Category category,
            String dishName,
            int page,
            int size) {

        validator.validateCriteria(page, size);

        return repository.findIngredientsByCriteria(name, category, dishName, page, size);
    }
    public Map<String, StockValue> verifyStockAt(Instant instant) {

        try (Connection conn = dataSource.getConnection()) {

            List<IngredientEntity> ingredients = repository.findAll(conn);

            Map<String, StockValue> result = new HashMap<>();

            for (IngredientEntity i : ingredients) {

                // IMPORTANT: charger mouvements
                i.setStockMovementList(repository.loadMovements(conn, i.getId()));

                StockValue stock = i.getStockValueAt(instant);

                result.put(i.getName(), stock);
            }

            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public StockValue getStockAt(Integer id,Instant instant) {
        try (Connection conn=dataSource.getConnection()){
            IngredientEntity ingredient= repository.findById(conn,id);
            if(ingredient == null) {
                throw new RuntimeException("Ingredient not found");
            }
            ingredient.setStockMovementList(
                    repository.loadMovements(conn, ingredient.getId())

            );
            return ingredient.getStockValueAt(instant);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public IngredientEntity getIngredientById(Integer id) {
        try (Connection conn=dataSource.getConnection()){
            return repository.findById(conn,id);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
