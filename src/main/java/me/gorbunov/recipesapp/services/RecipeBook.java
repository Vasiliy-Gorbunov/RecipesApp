package me.gorbunov.recipesapp.services;

import me.gorbunov.recipesapp.model.Recipe;

import java.util.Map;

public interface RecipeBook {

    void addNewRecipe(Recipe recipe);

    Recipe getRecipe(int id);
}
