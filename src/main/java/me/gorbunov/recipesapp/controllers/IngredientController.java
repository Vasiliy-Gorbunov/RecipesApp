package me.gorbunov.recipesapp.controllers;

import me.gorbunov.recipesapp.model.Ingredient;
import me.gorbunov.recipesapp.services.IngredientBook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientBook ingredientBook;

    public IngredientController(IngredientBook ingredientBook) {
        this.ingredientBook = ingredientBook;
    }


    @PostMapping("/")
    public ResponseEntity<Integer> addIngredient(@RequestBody Ingredient ingredient) {
        Integer id = ingredientBook.addIngredient(ingredient);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Integer id) {
        Ingredient ingredient = ingredientBook.getIngredient(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }
}
