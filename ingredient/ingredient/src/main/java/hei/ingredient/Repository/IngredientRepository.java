package hei.ingredient.Repository;

import hei.ingredient.DishIdDTO;
import hei.ingredient.Entity.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IngredientRepository {
    private final DataSource dataSource;

    public IngredientRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

   public List<IngredientEntity> findIngredients(int page, int size) {
        int offset = (page - 1) * size;
        List<IngredientEntity> ingredients = new ArrayList<>();

        String sql = """
SELECT i.id, i.name, i.price, i.category, di.id_dish  
FROM ingredient i inner join dishIngredient di 
on i.id=di.id_ingredient
ORDER BY id LIMIT ? OFFSET ?
""";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, size);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                IngredientEntity ing = new IngredientEntity();
                ing.setId(rs.getInt("id"));
                ing.setName(rs.getString("name"));
                ing.setPrice(rs.getDouble("price"));
                ing.setCategory(Category.valueOf(rs.getString("category")));
                DishEntity dish = new DishEntity();
                dish.setId(rs.getInt("id_dish"));
                ing.setDish(dish);
                ingredients.add(ing);
            }

            return ingredients;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    public boolean existsByName(String name) {
        try (Connection conn=dataSource.getConnection()) {
            String sql = "SELECT COUNT(1) FROM ingredient WHERE name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        public IngredientEntity insertIngredient(IngredientEntity ingredient)  {
           /* String sql = "INSERT INTO ingredient (name, price, category, id_dish) VALUES (?, ?, ?, ?)";*/
            String sql = "INSERT INTO ingredient (name, price, category) VALUES (?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            try (Connection conn=dataSource.getConnection()) {
                PreparedStatement ps=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, ingredient.getName());
                ps.setDouble(2, ingredient.getPrice());
                ps.setObject(3, ingredient.getCategory().name(), java.sql.Types.OTHER);
               /* ps.setObject(4, ingredient.getDish() != null ? ingredient.getDish().getId() : null);*/
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    ingredient.setId(rs.getInt(1));
                }
                return ingredient;
            }catch (SQLException e){
                throw new RuntimeException(e);
        }
    }
   public List<IngredientEntity> findIngredientsByCriteria(
            String ingredientName,
            Category category,
            String dishName,
            int page,
            int size) {

        List<IngredientEntity> ingredients = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
        SELECT i.id, i.name, i.price, i.category, di.id_dish,d.name as dishName
        FROM ingredient i JOIN dishIngredient di ON i.id = di.id_ingredient
        JOIN dish d ON di.id_dish = d.id
        WHERE 1=1
    """);

        List<Object> params = new ArrayList<>();

        // 🔍 filtres dynamiques
        if (ingredientName != null && !ingredientName.isBlank()) {
            sql.append(" AND i.name ILIKE ?");
            params.add("%" + ingredientName + "%");
        }

        if (category != null) {
            sql.append(" AND i.category = ?::ingredient_category_enum");
            params.add(category.name());
        }

        if (dishName != null && !dishName.isBlank()) {
            sql.append(" AND d.name ILIKE ?");
            params.add("%" + dishName + "%");
        }

        // 📄 pagination
        sql.append(" ORDER BY i.id LIMIT ? OFFSET ?");
        params.add(size);
        params.add((page - 1) * size);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // inject params
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                IngredientEntity ing = new IngredientEntity();
                ing.setId(rs.getInt("id"));
                ing.setName(rs.getString("name"));
                ing.setPrice(rs.getDouble("price"));
                ing.setCategory(Category.valueOf(rs.getString("category")));

                if (rs.getObject("id_dish") != null) {
                    DishEntity dish = new DishEntity();
                    dish.setId(rs.getInt("id_dish"));
                    dish.setName(rs.getString("dishName"));
                    ing.setDish(dish);
                }

                ingredients.add(ing);
            }

            return ingredients;

        } catch (SQLException e) {
            throw new RuntimeException("Error filtering ingredients", e);
        }
    }
    public List<DishIngredientEntity> findIngredientByDishid(Integer dishId) {
        List<DishIngredientEntity> ingredients = new ArrayList<>();
        String sql= """
                select i.id as ingId, i.name as ingName, category ingCate,di.id_dish AS dish_id, di.quantity_required,di.unit
                from dishIngredient di join ingredient i  on  i.id=di.id_ingredient
                join dish d on di.id_dish = d.id
                where di.id_dish = ?""";

        try (Connection con=dataSource.getConnection()){
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, dishId);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                DishIngredientEntity ing = new DishIngredientEntity();
                IngredientEntity i=new IngredientEntity();
                i.setId(rs.getInt("ingId"));
                i.setName(rs.getString("ingName"));
                i.setCategory(Category.valueOf(rs.getString("ingCate")));
                ing.setIngredient(i);
                DishEntity d=new DishEntity();
                d.setId(rs.getInt("dish_id"));
                ing.setDish(d);
                ing.setQuantity(rs.getDouble("quantity_required"));
                ing.setUnit(UnitEnum.valueOf(rs.getString("unit")));
                ingredients.add(ing);
            }
            return ingredients;
        }catch (SQLException e){
            throw new RuntimeException("Error filtering ingredients", e);
        }
    }

}
