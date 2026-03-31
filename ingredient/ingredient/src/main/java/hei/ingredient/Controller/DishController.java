package hei.ingredient.Controller;

import hei.ingredient.Entity.DishEntity;
import hei.ingredient.Service.DishService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
