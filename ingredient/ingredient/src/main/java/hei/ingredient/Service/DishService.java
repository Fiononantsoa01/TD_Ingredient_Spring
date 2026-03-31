package hei.ingredient.Service;

import hei.ingredient.Entity.DishEntity;
import hei.ingredient.Repository.DishRepository;
import hei.ingredient.Validator.DishValidator;
import org.springframework.stereotype.Service;

@Service
public class DishService {
    private final DishRepository repository;
    private final DishValidator validator;
    public DishService(DishRepository repository, DishValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }
    public DishEntity getDishById(Integer id) {
        validator.validateId(id);
            DishEntity dish = repository.findDishById(id);
        return dish;
    }
}
