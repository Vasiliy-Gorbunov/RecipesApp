package me.gorbunov.recipesapp.controllers;

import me.gorbunov.recipesapp.model.Recipe;
import me.gorbunov.recipesapp.services.RecipeBook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeBook recipeBook;

    public RecipeController(RecipeBook recipeBook) {
        this.recipeBook = recipeBook;
    }

    @PostMapping("/")
    public ResponseEntity<Integer> addRecipe(@RequestBody Recipe recipe) {
        Integer id = recipeBook.addNewRecipe(recipe);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Integer id) {
        Recipe recipe = recipeBook.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/")
    public ResponseEntity<Map<Integer, Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeBook.getAllRecipes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Integer id, @RequestBody Recipe recipe) {
        if (recipeBook.updateRecipe(id, recipe) == null) {
            return ResponseEntity.notFound().build();
        }
        Recipe newRecipe = recipeBook.updateRecipe(id, recipe);
        return ResponseEntity.ok(newRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteRecipe(@PathVariable Integer id) {
        if (!recipeBook.deleteRecipe(id)) {
            return ResponseEntity.notFound().build();
        }
        recipeBook.deleteRecipe(id);
        return ResponseEntity.ok(id);
    }
}
