package hei.ingredient.Entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class DishDto {
    private DishEntity dish;
    private List<DishIngredientEntity> ingredients;
}