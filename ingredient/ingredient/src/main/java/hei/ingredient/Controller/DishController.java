package hei.ingredient.Controller;

import hei.ingredient.DTO.DishCreateRequest;
import hei.ingredient.Entity.DishEntity;
import hei.ingredient.Entity.DishIngredientEntity;
import hei.ingredient.Exception.BadRequestException;
import hei.ingredient.Service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishController {
    private final DishService dishService;
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<DishEntity> getDishById(@PathVariable Integer id) {
            DishEntity dish = dishService.getDishById(id);
            return ResponseEntity.ok(dish);
    }
    @GetMapping("/all")
    public ResponseEntity< List<DishEntity>> getAll() {
        List<DishEntity> allDishes=dishService.getAllDishes();
        return ResponseEntity.ok(allDishes);
    }
   /*@PostMapping
    public ResponseEntity<DishEntity> saveDish(@RequestBody DishEntity dish) {
        try {
            DishEntity savedDish = dishService.saveDish(dish);
            HttpStatus status = (dish.getId() == null) ? HttpStatus.CREATED : HttpStatus.OK;
            return ResponseEntity.status(status).body(savedDish);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        }
    }*/
   @PostMapping("/createDishes")
   public ResponseEntity<List<DishEntity>> createDishes(
           @RequestBody List<DishCreateRequest> dishes
   ) {

       List<DishEntity> created = dishService.createDishes(dishes);

       return ResponseEntity.status(201).body(created);
   }
   @PutMapping("/{dishId}/ingredients")
   public ResponseEntity<?> saveDish(
           @PathVariable Integer dishId,
           @RequestBody List<DishIngredientEntity> list) {

       dishService.saveDish(dishId, list);

       return ResponseEntity.ok("Updated successfully");
   }
    @GetMapping("/search")
    public ResponseEntity<List<DishEntity>> searchByIngredient(
            @RequestParam String ingredientName) {

        List<DishEntity> dishes = dishService.findDishesByIngredientName(ingredientName);
        if (dishes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dishes);
        }
        return ResponseEntity.ok(dishes);
    }
    @GetMapping("/getDishes")
    public List<DishEntity> getDishes(
            @RequestParam(required = false) Double priceUnder,
            @RequestParam(required = false) Double priceOver,
            @RequestParam(required = false) String name
    ) {
        return dishService.getFilteredDishes(priceUnder, priceOver, name);
    }
}
