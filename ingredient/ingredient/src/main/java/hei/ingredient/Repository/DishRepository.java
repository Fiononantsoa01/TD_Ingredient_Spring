package hei.ingredient.Repository;

import hei.ingredient.Entity.Category;
import hei.ingredient.Entity.DishEntity;
import hei.ingredient.Entity.DishTypeEnum;
import hei.ingredient.Entity.IngredientEntity;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DishRepository {
    private final DataSource dataSource;
    public DishRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public DishEntity findDishById(Integer id) {
        try (Connection conn=dataSource.getConnection()){
            String sqlDish = "SELECT id, name, dish_type FROM dish WHERE id = ?";
            PreparedStatement psDish = conn.prepareStatement(sqlDish);
            psDish.setInt(1, id);
            ResultSet rsDish = psDish.executeQuery();
            if (rsDish.next()) {
                DishEntity dish = new DishEntity();
                dish.setId(rsDish.getInt("id"));
                dish.setName(rsDish.getString("name"));
                dish.setDishType(DishTypeEnum.valueOf(rsDish.getString("dish_type")));

                rsDish.close();
                psDish.close();
                String sqlIngredients = "SELECT id, name, price, category FROM ingredient WHERE id_dish = ?";
                PreparedStatement psIng = conn.prepareStatement(sqlIngredients);
                psIng.setInt(1, id);
                ResultSet rsIng = psIng.executeQuery();

               List<IngredientEntity> ingredients = new ArrayList<>();
                if (rsIng.next()) {
                    IngredientEntity ing = new IngredientEntity();
                    ing.setId(rsIng.getInt("id"));
                    ing.setName(rsIng.getString("name"));
                    ing.setPrice(rsIng.getDouble("price"));
                    ing.setCategory(Category.valueOf(rsIng.getString("category")));
                    ingredients.add(ing);
                }

                dish.setIngredients(ingredients);

                rsIng.close();
                psIng.close();
                conn.close();

                return dish;
            }
            else {
                rsDish.close();
                psDish.close();
                conn.close();
                return null;
            }
            //get ingredients

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
