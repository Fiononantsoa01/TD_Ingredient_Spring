package hei.ingredient.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DishEntity {
    private Integer id;
    private String name;
    private DishTypeEnum dishType;
    private List<DishIngredientEntity> ingredients;
    private Double price;

   /* Double getPrice() {
        for (IngredientEntity ingredient : ingredients) {
            if (ingredient.getDish().getId().equals(id)) {
                return ingredient.getPrice();
            }
        }
        throw new RuntimeException();
    }

    public DishEntity(Integer id) {
        this.id = id;
    }*/
    Double getDishCost(){
        DishEntity dish=new DishEntity();
        return dish.getPrice();
    }
    Double getGrossMargin(){
        DishEntity dish=new DishEntity();
        return dish.getPrice();
    }
}
