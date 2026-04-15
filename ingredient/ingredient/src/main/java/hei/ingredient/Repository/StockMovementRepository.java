package hei.ingredient.Repository;

import hei.ingredient.Entity.StockMovement;
import hei.ingredient.Entity.StockValue;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
@Repository
public class StockMovementRepository {
    public void insertStockMovements(Connection conn, List<StockMovement> movements, int ingredientId) throws SQLException {

        String sql = """
    INSERT INTO stock_movement (id_ingredient, quantity, type, unit, creation_datetime)
    VALUES (?, ?, ?, ?, ?)
""";


        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            for (StockMovement sm : movements) {


                ps.setInt(1, ingredientId);
                StockValue value = sm.getValue();
                if(value == null) {
                    throw new RuntimeException("Value is null");
                }
               // 🔹 Quantity
                ps.setDouble(2, value.getQuantity());

                // 🔹 Type
                ps.setObject(3, sm.getType().name(), Types.OTHER);

                // 🔹 Unit (vient de value)
                ps.setObject(4, value.getUnit().name(), Types.OTHER);

                // 🔹 Date
                ps.setTimestamp(5, Timestamp.from(sm.getCreationDateTime()));

                ps.addBatch();
            }

            ps.executeBatch();
        }
    }
}
