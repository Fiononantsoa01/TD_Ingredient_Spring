package hei.ingredient.Entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class DishEntity {
    private Integer id;
    private String name;
    private DishTypeEnum dishType;
    private List<IngredientEntity> ingredients;

    Double getPrice() {
        for (IngredientEntity ingredient : ingredients) {
            if (ingredient.getDish().getId().equals(id)) {
                return ingredient.getPrice();
            }
        }
        throw new RuntimeException();
    }
}
