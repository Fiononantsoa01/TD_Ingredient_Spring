package hei.ingredient.Repository;

import hei.ingredient.Entity.DishIngredientEntity;
import hei.ingredient.Entity.IngredientEntity;
import hei.ingredient.Entity.DishEntity;
import hei.ingredient.Entity.Unit;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DishIngredientRepository {

    private final DataSource dataSource;

    public DishIngredientRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(DishIngredientEntity entity) throws SQLException {
        String sql = "INSERT INTO dish_ingredient(dish_id, ingredient_id, quantity_required, unit) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, entity.getDishId());
            ps.setInt(2, entity.getIngredientId());
            ps.setDouble(3, entity.getQuantityRequired());
            ps.setObject(4, entity.getUnit().name(), Types.OTHER);

            ps.executeUpdate();
        }
    }

    public List<DishIngredientEntity> findByDishId(int dishId) throws SQLException {
        String sql = """
                SELECT di.dish_id, di.ingredient_id, di.quantity_required, di.unit,
                       i.id as ing_id, i.name, i.price, i.category
                FROM dish_ingredient di
                JOIN ingredient i ON di.ingredient_id = i.id
                WHERE di.dish_id = ?
                """;
        List<DishIngredientEntity> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dishId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DishIngredientEntity die = new DishIngredientEntity();
                IngredientEntity ing = new IngredientEntity();
                ing.setId(rs.getInt("ing_id"));
                ing.setName(rs.getString("name"));
                ing.setPrice(rs.getDouble("price"));
                // Assumes Category enum exists
                ing.setCategory(hei.ingredient.Entity.Category.valueOf(rs.getString("category")));

                die.setIngredient(ing);
                die.setDish(new DishEntity(rs.getInt("dish_id"), null, null, null));
                die.setQuantityRequired(rs.getDouble("quantity_required"));
                die.setUnit(Unit.valueOf(rs.getString("unit")));

                result.add(die);
            }
        }
        return result;
    }

    public void deleteByDishId(int dishId) throws SQLException {
        String sql = "DELETE FROM dish_ingredient WHERE dish_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dishId);
            ps.executeUpdate();
        }
    }
}