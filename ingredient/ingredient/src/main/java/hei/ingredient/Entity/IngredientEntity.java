package hei.ingredient.Entity;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class IngredientEntity {
    private Integer id;
    private String name;
    private Double price;
    private Category category;
    private DishEntity dish;

    public IngredientEntity(Integer id, String name, Double price, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    String getDishName() {
        return dish.getName();
    }
}
