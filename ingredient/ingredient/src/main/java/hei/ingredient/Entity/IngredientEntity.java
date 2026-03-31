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

}
