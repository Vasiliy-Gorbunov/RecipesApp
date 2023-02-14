package me.gorbunov.recipesapp.services;

import me.gorbunov.recipesapp.model.Ingredient;
import org.springframework.stereotype.Service;

public interface IngredientBook {
    Integer addIngredient(Ingredient ingredient);

    Ingredient getIngredient(int id);
}
