package hei.ingredient.Controller;

import hei.ingredient.Entity.Category;
import hei.ingredient.Entity.DishIngredientEntity;
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
    public ResponseEntity<List<IngredientEntity>> getIngredients(
            @RequestParam int page,
            @RequestParam int size
    )
    {
        List<IngredientEntity> ingredients = service.getIngredients(page, size);
        return ResponseEntity.ok(ingredients);
    }
    @GetMapping("/searchByDish/{idDish}")
    public ResponseEntity<List<DishIngredientEntity>> getIngredientsByDish(@PathVariable Integer idDish)
    {
        List<DishIngredientEntity> ingredients= service.getIngredientByDish(idDish);
        return ResponseEntity.ok(ingredients);
    }
    /*@PostMapping
    public ResponseEntity<List<IngredientEntity>> createIngredients(@RequestBody List<IngredientEntity> newIngredients) {
        List<IngredientEntity> created = service.createIngredients(newIngredients);
        return ResponseEntity.ok(created);
    }*/@PostMapping
    public IngredientEntity createIngredients(@RequestBody IngredientEntity newIngredients) {
        return service.saveIngredients(newIngredients);
    }

    @GetMapping("/search")
    public ResponseEntity<List<IngredientEntity>> searchIngredients(
            @RequestParam(required = false) String ingredientName,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) String dishName,
            @RequestParam int page,
            @RequestParam int size) {

        List<IngredientEntity> result =
                service.findIngredientsByCriteria(ingredientName, category, dishName, page, size);

        if (result.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.ok(result); // 200
    }
}
