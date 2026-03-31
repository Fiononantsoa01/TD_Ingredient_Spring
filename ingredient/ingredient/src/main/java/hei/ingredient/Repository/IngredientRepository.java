package hei.ingredient.Repository;

import hei.ingredient.Entity.Category;
import hei.ingredient.Entity.IngredientEntity;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    }
}
