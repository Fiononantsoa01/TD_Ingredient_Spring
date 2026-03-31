package hei.ingredient.Entity;

import lombok.*;
import org.springframework.format.annotation.DurationFormat;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class DishIngredientEntity {
    private IngredientEntity id;
    private IngredientEntity ingredient;
    private DishEntity dish;
    private Double quantityRequired;
    private Unit unit;

    public DishIngredientEntity(DishEntity dish, IngredientEntity ing, double quantityRequired, Unit unit) {
    }

    public Integer getIngredientId() {
       return id.getId();
   }
   public Integer getDishId() {
       return dish.getId();
   }
}
