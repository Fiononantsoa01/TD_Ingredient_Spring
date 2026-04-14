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
  /*  public StockValue getStockValueAt(Instant instant) {

        double total = 0.0;
        UnitEnum unit = null;

        if (stockMovementList == null || stockMovementList.isEmpty()) {
            return new StockValue(0.0, null);
        }

        for (StockMovement sm : stockMovementList) {

            // ignorer les mouvements après la date
            if (sm.getCreationDateTime().isAfter(instant)) {
                continue;
            }

            // garder une unité (on suppose même unité pour tous)
            if (unit == null) {
                unit = sm.getUnit();
            }

            if (sm.getType() == MovementTypeEnum.IN) {
                total += sm.getValue().getQuantity();
            } else if (sm.getType() == MovementTypeEnum.OUT) {
                total -= sm.getValue().getQuantity();
            }
        }

        return new StockValue(total, unit);
    }


  /*  String getDishName() {
        return dish.getName();
    }*/
}
