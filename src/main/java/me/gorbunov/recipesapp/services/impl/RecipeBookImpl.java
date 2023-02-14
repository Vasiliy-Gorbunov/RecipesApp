package me.gorbunov.recipesapp.services.impl;

import me.gorbunov.recipesapp.model.Recipe;
import me.gorbunov.recipesapp.services.RecipeBook;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeBookImpl implements RecipeBook {

    private static final Map<Integer, Recipe> RECIPE_BOOK = new HashMap<>();
    private static int id = 0;


    @Override
    public Integer addNewRecipe(Recipe recipe) {
        RECIPE_BOOK.put(id, recipe);
        return id++;
    }

    @Override
    public Recipe getRecipe(int id) {
        return RECIPE_BOOK.get(id);
    }
}
