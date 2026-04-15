package hei.ingredient.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IngredientEntity {
    private Integer id;
    private String name;
    private Double price;
    private Category category;
   /* private DishEntity dish;*/
    private List<StockMovement> stockMovementList;
    public StockValue getStockValueAt(Instant instant) {

        double total = 0.0;
        UnitEnum unit = null;

        if (stockMovementList == null || stockMovementList.isEmpty()) {
            return new StockValue(0.0, null);
        }

        for (StockMovement sm : stockMovementList) {

            // 🔥 sécurité
            if (sm == null || sm.getValue() == null || sm.getCreationDateTime() == null) {
                continue;
            }

            // ignorer après instant
            if (sm.getCreationDateTime().isAfter(instant)) {
                continue;
            }

            // unité (une seule unité supposée)
            if (unit == null) {
                unit = sm.getValue().getUnit();
            }

            double qty = sm.getValue().getQuantity();

            if (sm.getType() == MovementTypeEnum.IN) {
                total += qty;
            } else if (sm.getType() == MovementTypeEnum.OUT) {
                total -= qty;
            }
        }

        return new StockValue(total, unit);
    }
}
