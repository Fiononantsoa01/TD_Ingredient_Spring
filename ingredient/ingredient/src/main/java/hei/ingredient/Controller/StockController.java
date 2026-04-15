package hei.ingredient.Controller;

import hei.ingredient.Entity.StockValue;
import hei.ingredient.Service.IngredientService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final IngredientService service;

    public StockController(IngredientService service) {
        this.service = service;
    }

    // 🔥 TEST STOCK À UNE DATE
    @GetMapping("/verify")
    public Map<String, StockValue> verifyAllStocks() {

        Instant instant = Instant.parse("2024-01-06T12:00:00Z");

        return service.verifyStockAt(instant);
    }}