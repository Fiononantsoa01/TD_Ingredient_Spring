package hei.ingredient.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.stereotype.Component;

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
    private Category category;;


  /*  String getDishName() {
        return dish.getName();
    }*/
}
