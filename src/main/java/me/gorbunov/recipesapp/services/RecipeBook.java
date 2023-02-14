package me.gorbunov.recipesapp.services;

import me.gorbunov.recipesapp.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.Map;
public interface RecipeBook {

    Integer addNewRecipe(Recipe recipe);

    Recipe getRecipe(int id);
}
