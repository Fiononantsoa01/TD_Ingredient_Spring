package hei.ingredient.Repository;

import hei.ingredient.Entity.Category;
import hei.ingredient.Entity.IngredientEntity;
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

        String sql = "SELECT id, name, price, category FROM ingredient ORDER BY id LIMIT ? OFFSET ?";

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
            String sql = "INSERT INTO ingredient (name, price, category, id_dish) VALUES (?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            try (Connection conn=dataSource.getConnection()) {
                PreparedStatement ps=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, ingredient.getName());
                ps.setDouble(2, ingredient.getPrice());
                ps.setObject(3, ingredient.getCategory().name(), java.sql.Types.OTHER);
                ps.setObject(4, ingredient.getDish() != null ? ingredient.getDish().getId() : null);

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

}
