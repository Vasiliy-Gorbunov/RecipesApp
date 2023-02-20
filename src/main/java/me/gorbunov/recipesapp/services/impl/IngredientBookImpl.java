package me.gorbunov.recipesapp.services.impl;

import me.gorbunov.recipesapp.model.Ingredient;
import me.gorbunov.recipesapp.services.IngredientBook;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientBookImpl implements IngredientBook {

    private static final Map<Integer, Ingredient> INGREDIENT_BOOK = new HashMap<>();
    private static int id = 0;

    @Override
    public Integer addIngredient(Ingredient ingredient) {
        INGREDIENT_BOOK.put(id, ingredient);
        return id++;
    }

    @Override
    public Ingredient getIngredient(int id) {
        return INGREDIENT_BOOK.get(id);
    }

    @Override
    public Map<Integer, Ingredient> getAllIngredients() {
        return INGREDIENT_BOOK;
    }

    @Override
    public Ingredient updateIngredient(int id, Ingredient ingredient) {
        if (INGREDIENT_BOOK.containsKey(id)) {
            INGREDIENT_BOOK.replace(id, ingredient);
            return INGREDIENT_BOOK.get(id);
        }
        return null;
    }

    @Override
    public boolean deleteIngredient(int id) {
        if (INGREDIENT_BOOK.containsKey(id)) {
            INGREDIENT_BOOK.remove(id);
            return true;
        }
        return false;
    }
}
