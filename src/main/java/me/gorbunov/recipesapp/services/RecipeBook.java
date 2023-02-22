package me.gorbunov.recipesapp.services;

import me.gorbunov.recipesapp.model.Recipe;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
public interface RecipeBook {

    Path getRecipesAsText() throws IOException;

    Integer addNewRecipe(Recipe recipe);

    Recipe getRecipe(int id);

    Map<Integer, Recipe> getAllRecipes();

    Recipe updateRecipe(int id, Recipe recipe);

    boolean deleteRecipe(int id);

    void readFromFile();
}
