package hei.ingredient.Repository;

import hei.ingredient.Entity.*;
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
               /** String sqlIngredients = "SELECT id, name, price, category FROM ingredient WHERE id_dish = ?";
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
                psIng.close();*/
                String sqlIngredients= """
                        SELECT i.id, i.name, i.price, i.category FROM ingredient i
                         inner join dishIngredient di on i.id= di.id_ingredient
                         where di.id_dish = ? """;
                PreparedStatement psIngredients = conn.prepareStatement(sqlIngredients);
                psIngredients.setInt(1, id);
                ResultSet rsIngredients = psIngredients.executeQuery();
                List<DishIngredientEntity> ingredients = new ArrayList<>();
                while (rsIngredients.next()) {
                    DishIngredientEntity dishIngredient = new DishIngredientEntity();
                    IngredientEntity ingredient = new IngredientEntity();
                    ingredient.setId(rsIngredients.getInt("id"));
                    ingredient.setName(rsIngredients.getString("name"));
                    ingredient.setPrice(rsIngredients.getDouble("price"));
                    ingredient.setCategory(Category.valueOf(rsIngredients.getString("category")));
                    dishIngredient.setIngredient(ingredient);
                    ingredients.add(dishIngredient);
                }
                dish.setIngredients(ingredients);
                rsIngredients.close();
                psIngredients.close();

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
    /*public boolean existsById( Integer id)  {
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
    public boolean existsByName(String name) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT 1 FROM dish WHERE name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public DishEntity findByName(String name) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, dish_type FROM dish WHERE name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                DishEntity dish = new DishEntity();
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setDishType(DishTypeEnum.valueOf(rs.getString("dish_type")));
                return dish;
            }
            return null;

        } catch (SQLException e) {
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
    public List<DishEntity> findDishesByIngredientName(String ingredientName) {
        List<DishEntity> dishes = new ArrayList<>();

        String sql = """
        SELECT d.id as dish_id, d.name as dish_name, d.dish_type,
               i.id as ing_id, i.name as ing_name, i.price, i.category
        FROM dish d
        JOIN ingredient i ON i.id_dish = d.id
        WHERE i.name ILIKE ?
        ORDER BY d.id
        """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Recherche insensible à la casse
            ps.setString(1, "%" + ingredientName + "%");

            ResultSet rs = ps.executeQuery();

            DishEntity currentDish = null;
            int currentDishId = -1;

            while (rs.next()) {
                int dishId = rs.getInt("dish_id");

                // Si nouveau plat
                if (currentDish == null || currentDishId != dishId) {
                    currentDish = new DishEntity();
                    currentDish.setId(dishId);
                    currentDish.setName(rs.getString("dish_name"));
                    currentDish.setDishType(DishTypeEnum.valueOf(rs.getString("dish_type")));
                    currentDish.setIngredients(new ArrayList<>());
                    dishes.add(currentDish);
                    currentDishId = dishId;
                }

                // Ajouter l'ingrédient
                IngredientEntity ing = new IngredientEntity();
                ing.setId(rs.getInt("ing_id"));
                ing.setName(rs.getString("ing_name"));
                ing.setPrice(rs.getDouble("price"));
                ing.setCategory(Category.valueOf(rs.getString("category")));
                currentDish.getIngredients().add(ing);
            }

            rs.close();
            return dishes;

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching dishes by ingredient name", e);
        }
    }*/

}
