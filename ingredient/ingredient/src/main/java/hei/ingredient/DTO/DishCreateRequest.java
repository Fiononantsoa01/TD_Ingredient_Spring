package hei.ingredient.DTO;

import hei.ingredient.Entity.DishTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishCreateRequest {
    private String name;
    private DishTypeEnum dishType;
    private Double price;
}