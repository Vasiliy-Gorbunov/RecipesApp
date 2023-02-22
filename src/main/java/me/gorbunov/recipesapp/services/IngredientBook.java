package me.gorbunov.recipesapp.services;

import me.gorbunov.recipesapp.model.Ingredient;

import java.util.Map;

public interface IngredientBook {
    Integer addIngredient(Ingredient ingredient);

    Ingredient getIngredient(int id);

    Map<Integer, Ingredient> getAllIngredients();

    Ingredient updateIngredient(int id, Ingredient ingredient);

    boolean deleteIngredient(int id);

    void readFromFile();
}
