package me.gorbunov.recipesapp.services;

import me.gorbunov.recipesapp.model.Recipe;

import java.util.Map;
public interface RecipeBook {

    Integer addNewRecipe(Recipe recipe);

    Recipe getRecipe(int id);

    Map<Integer, Recipe> getAllRecipes();

    Recipe updateRecipe(int id, Recipe recipe);

    boolean deleteRecipe(int id);

    void readFromFile();
}
