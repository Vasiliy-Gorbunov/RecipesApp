package me.gorbunov.recipesapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.gorbunov.recipesapp.model.Ingredient;
import me.gorbunov.recipesapp.services.FilesService;
import me.gorbunov.recipesapp.services.IngredientBook;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientBookImpl implements IngredientBook {

    private final FilesService filesService;

    private static Map<Integer, Ingredient> INGREDIENT_BOOK = new HashMap<>();
    private static int id = 0;

    public IngredientBookImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Integer addIngredient(Ingredient ingredient) {
        INGREDIENT_BOOK.put(id, ingredient);
        saveToFile();
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
            saveToFile();
            return INGREDIENT_BOOK.get(id);
        }
        return null;
    }

    @Override
    public boolean deleteIngredient(int id) {
        if (INGREDIENT_BOOK.containsKey(id)) {
            INGREDIENT_BOOK.remove(id);
            saveToFile();
            return true;
        }
        return false;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(INGREDIENT_BOOK);
            filesService.saveToIngredientsFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        String json = filesService.readFromIngredientsFile();
        try {
            INGREDIENT_BOOK = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
