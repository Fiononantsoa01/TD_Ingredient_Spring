package hei.ingredient.Repository;

import hei.ingredient.Entity.*;
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

    public boolean existsByName(String name) {
        String sql = "SELECT 1 FROM ingredient WHERE name = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public IngredientEntity insertIngredient(Connection conn, IngredientEntity ingredient) throws SQLException {
        String sql = "INSERT INTO ingredient(name, price, category) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, ingredient.getName());
            if (ingredient.getPrice() != null) ps.setDouble(2, ingredient.getPrice());
            else ps.setNull(2, Types.NUMERIC);
            ps.setObject(3, ingredient.getCategory().name(), Types.OTHER);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) ingredient.setId(rs.getInt(1));
            return ingredient;
        }
    }

    public List<IngredientEntity> findAll(int page, int size) {
        List<IngredientEntity> list = new ArrayList<>();
        String sql = "SELECT id, name, price, category FROM ingredient ORDER BY id LIMIT ? OFFSET ?";
        int offset = (page - 1) * size;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, size);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                IngredientEntity ing = new IngredientEntity();
                ing.setId(rs.getInt("id"));
                ing.setName(rs.getString("name"));
                ing.setPrice(rs.getObject("price") != null ? rs.getDouble("price") : null);
                ing.setCategory(Category.valueOf(rs.getString("category")));
                list.add(ing);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}