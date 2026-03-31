package hei.ingredient.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Product {
    private Integer id;
    private String name;
    private Double price;
    private LocalDate creationDate;
    private ProductCategory productCategory;

    public String getCategoryName() {
        return productCategory != null ? productCategory.getName() : null;
    }
}
