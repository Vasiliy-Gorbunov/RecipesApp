package me.gorbunov.recipesapp.controllers;

import jakarta.websocket.server.PathParam;
import me.gorbunov.recipesapp.model.Recipe;
import me.gorbunov.recipesapp.services.RecipeBook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
