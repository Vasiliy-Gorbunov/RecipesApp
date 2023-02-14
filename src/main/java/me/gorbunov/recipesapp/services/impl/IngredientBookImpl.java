package me.gorbunov.recipesapp.services.impl;

import me.gorbunov.recipesapp.model.Ingredient;
import me.gorbunov.recipesapp.services.IngredientBook;

import java.util.HashMap;
import java.util.Map;

public class IngredientBookImpl implements IngredientBook {

    private static final Map<Integer, Ingredient> INGREDIENT_BOOK = new HashMap<>();
    private static int id = 0;

    @Override
    public void addIngredient(Ingredient ingredient) {
        INGREDIENT_BOOK.put(id++, ingredient);
    }

    @Override
    public Ingredient getIngredient(int id) {
        return INGREDIENT_BOOK.get(id);
    }
}
