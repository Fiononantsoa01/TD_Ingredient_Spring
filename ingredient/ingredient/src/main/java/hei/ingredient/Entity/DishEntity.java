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
   /* private List<IngredientEntity> ingrediens;*/
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
    Double getDishCost(List<DishIngredientEntity> ingredients) {
        Double totalPrice=0.0;
        for(DishIngredientEntity e:ingredients){
            Double quantity= e.getQuantity();
            Double unitPrice=e.getIngredient().getPrice();
            totalPrice=totalPrice+quantity*unitPrice;
        }
        return totalPrice;
    }
    Double getDishMargin(DishEntity dish, List<DishIngredientEntity> ingredients) {

        if (dish.getPrice() == null) {
            throw new RuntimeException("Le prix de vente du plat est null");
        }

        double cost = getDishCost(ingredients);
        return dish.getPrice() - cost;
    }
}
