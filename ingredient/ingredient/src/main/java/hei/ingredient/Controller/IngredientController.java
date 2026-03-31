package hei.ingredient.Controller;

import hei.ingredient.Entity.IngredientEntity;
import hei.ingredient.Service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<IngredientEntity>> getIngredients(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(service.getIngredients(page, size));
    }

    @PostMapping
    public ResponseEntity<IngredientEntity> saveIngredient(@RequestBody IngredientEntity ingredient) {
        return ResponseEntity.ok(service.saveIngredient(ingredient));
    }
}