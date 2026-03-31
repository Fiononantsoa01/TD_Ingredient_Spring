package hei.ingredient.Controller;

import hei.ingredient.Entity.DishEntity;
import hei.ingredient.Service.DishService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public ResponseEntity<DishEntity> saveDish(@RequestBody DishEntity dish) {
        return ResponseEntity.ok(dishService.saveDish(dish));
    }
}
