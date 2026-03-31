package hei.ingredient.Repository;

import hei.ingredient.Entity.Category;
import hei.ingredient.Entity.DishEntity;
import hei.ingredient.Entity.DishTypeEnum;
import hei.ingredient.Entity.IngredientEntity;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DishRepository {
    private final DataSource dataSource;
    public DishRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public DishEntity findDishById(Integer id) {
        try (Connection conn=dataSource.getConnection()){
            String sqlDish = "SELECT id, name, dish_type FROM dish WHERE id = ?";
            PreparedStatement psDish = conn.prepareStatement(sqlDish);
            psDish.setInt(1, id);
            ResultSet rsDish = psDish.executeQuery();
            if (rsDish.next()) {
                DishEntity dish = new DishEntity();
                dish.setId(rsDish.getInt("id"));
                dish.setName(rsDish.getString("name"));
                dish.setDishType(DishTypeEnum.valueOf(rsDish.getString("dish_type")));

                rsDish.close();
                psDish.close();
                String sqlIngredients = "SELECT id, name, price, category FROM ingredient WHERE id_dish = ?";
                PreparedStatement psIng = conn.prepareStatement(sqlIngredients);
                psIng.setInt(1, id);
                ResultSet rsIng = psIng.executeQuery();

                List<IngredientEntity> ingredients = new ArrayList<>();
                while (rsIng.next()) {
                    IngredientEntity ing = new IngredientEntity();
                    ing.setId(rsIng.getInt("id"));
                    ing.setName(rsIng.getString("name"));
                    ing.setPrice(rsIng.getDouble("price"));
                    ing.setCategory(Category.valueOf(rsIng.getString("category")));
                    ingredients.add(ing);
                }

                dish.setIngredients(ingredients);

                rsIng.close();
                psIng.close();
                conn.close();

                return dish;
            }
            else {
                rsDish.close();
                psDish.close();
                conn.close();
                return null;
            }
            //get ingredients

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public boolean existsById( Integer id)  {
        try (Connection conn=dataSource.getConnection()) {
            String sql = "SELECT 1 FROM dish WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public int insert(Connection conn, DishEntity dish) throws SQLException {
        String sql = "INSERT INTO dish(name, dish_type) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, dish.getName());
            ps.setObject(2, dish.getDishType().name(),java.sql.Types.OTHER);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                dish.setId(generatedId);
                return generatedId; // retourne l’ID généré
            } else {
                throw new SQLException("No ID generated");
            }
        }
    }
    public void update(Connection conn, DishEntity dish) throws SQLException {
        String sql = "UPDATE dish SET name = ?, dish_type = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dish.getName());
            ps.setObject(2, dish.getDishType().name(),java.sql.Types.OTHER);
            ps.setInt(3, dish.getId());
            ps.executeUpdate();
        }
    }
    public void updateDishIngredients(Connection conn, DishEntity dish) throws SQLException {

        // 1. supprimer anciennes associations
        String deleteSql = "UPDATE ingredient SET id_dish = NULL WHERE id_dish = ?";
        try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
            ps.setInt(1, dish.getId());
            ps.executeUpdate();
        }
        if (dish.getIngredients() != null) {
            String updateSql = "UPDATE ingredient SET id_dish = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {

                for (IngredientEntity ing : dish.getIngredients()) {
                    ps.setInt(1, dish.getId());
                    ps.setInt(2, ing.getId());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }
}
