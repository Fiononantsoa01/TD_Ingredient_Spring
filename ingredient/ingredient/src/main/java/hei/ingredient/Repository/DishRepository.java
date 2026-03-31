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
        String sql = "SELECT id, name, dish_type, selling_price FROM dish WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                DishEntity dish = new DishEntity();
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setDishType(DishTypeEnum.valueOf(rs.getString("dish_type")));
                dish.setSellingPrice(rs.getObject("selling_price") != null ? rs.getDouble("selling_price") : null);
                return dish;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsById(Integer id) {
        String sql = "SELECT 1 FROM dish WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insert(Connection conn, DishEntity dish) throws SQLException {
        String sql = "INSERT INTO dish(name, dish_type, selling_price) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, dish.getName());
            ps.setObject(2, dish.getDishType().name(), Types.OTHER);
            if (dish.getSellingPrice() != null) {
                ps.setDouble(3, dish.getSellingPrice());
            } else {
                ps.setNull(3, Types.NUMERIC);
            }

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                dish.setId(id);
                return id;
            } else {
                throw new SQLException("No ID generated for Dish");
            }
        }
    }

    public void update(Connection conn, DishEntity dish) throws SQLException {
        String sql = "UPDATE dish SET name = ?, dish_type = ?, selling_price = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dish.getName());
            ps.setObject(2, dish.getDishType().name(), Types.OTHER);
            if (dish.getSellingPrice() != null) {
                ps.setDouble(3, dish.getSellingPrice());
            } else {
                ps.setNull(3, Types.NUMERIC);
            }
            ps.setInt(4, dish.getId());
            ps.executeUpdate();
        }
    }
}