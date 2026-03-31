package hei.ingredient.Controller;

import hei.ingredient.Entity.IngredientEntity;
import hei.ingredient.Service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    ) {
        List<IngredientEntity> ingredients = service.getIngredients(page, size);
        return ResponseEntity.ok(ingredients);
    }
}
