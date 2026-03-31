package hei.ingredient.Controller;

import hei.ingredient.Entity.DishEntity;
import hei.ingredient.Service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    private final DishService service;

    public DishController(DishService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishEntity> getDish(@PathVariable Integer id) {
        DishEntity dish = service.getDishById(id);
        return dish != null ? ResponseEntity.ok(dish) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DishEntity> saveDish(@RequestBody DishEntity dish) {
        DishEntity saved = service.saveDish(dish);
        HttpStatus status = (dish.getId() == null) ? HttpStatus.CREATED : HttpStatus.OK;
        return ResponseEntity.status(status).body(saved);
    }
}