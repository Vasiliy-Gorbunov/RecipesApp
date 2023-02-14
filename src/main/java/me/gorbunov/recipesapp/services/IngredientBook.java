package me.gorbunov.recipesapp.services;

import me.gorbunov.recipesapp.model.Ingredient;

public interface IngredientBook {
    void addIngredient(Ingredient ingredient);

    Ingredient getIngredient(int id);
}
