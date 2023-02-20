package me.gorbunov.recipesapp.controllers;

import me.gorbunov.recipesapp.model.Ingredient;
import me.gorbunov.recipesapp.services.IngredientBook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping("/")
    public ResponseEntity<Map<Integer, Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientBook.getAllIngredients());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable Integer id, @RequestBody Ingredient ingredient) {
        Ingredient newIngredient = ingredientBook.updateIngredient(id, ingredient);
        if (newIngredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newIngredient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteIngredient(@PathVariable Integer id) {
        boolean delete = ingredientBook.deleteIngredient(id);
        if (!delete) {
            return ResponseEntity.notFound().build();
        }
        ingredientBook.deleteIngredient(id);
        return ResponseEntity.ok(id);
    }
}
