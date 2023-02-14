package me.gorbunov.recipesapp.services.impl;

import me.gorbunov.recipesapp.model.Recipe;
import me.gorbunov.recipesapp.services.RecipeBook;

import java.util.HashMap;
import java.util.Map;

public class RecipeBookImpl implements RecipeBook {

    private static final Map<Integer, Recipe> RECIPE_BOOK = new HashMap<>();
    private static int id = 0;


    @Override
    public void addNewRecipe(Recipe recipe) {
        RECIPE_BOOK.put(id++, recipe);
    }

    @Override
    public Recipe getRecipe(int id) {
        return RECIPE_BOOK.get(id);
    }
}
