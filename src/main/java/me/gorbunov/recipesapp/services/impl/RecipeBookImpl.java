package me.gorbunov.recipesapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.gorbunov.recipesapp.model.Recipe;
import me.gorbunov.recipesapp.services.FilesService;
import me.gorbunov.recipesapp.services.RecipeBook;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeBookImpl implements RecipeBook {

    private final FilesService filesService;

    private static Map<Integer, Recipe> RECIPE_BOOK = new HashMap<>();
    private static int id = 0;

    public RecipeBookImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Integer addNewRecipe(Recipe recipe) {
        RECIPE_BOOK.put(id, recipe);
        saveToFile();
        return id++;
    }

    @Override
    public Recipe getRecipe(int id) {
        return RECIPE_BOOK.get(id);
    }

    @Override
    public Map<Integer, Recipe> getAllRecipes() {
        return RECIPE_BOOK;
    }

    @Override
    public Recipe updateRecipe(int id, Recipe recipe) {
        if (RECIPE_BOOK.containsKey(id)) {
            RECIPE_BOOK.replace(id, recipe);
            saveToFile();
            return RECIPE_BOOK.get(id);
        }
        return null;
    }

    @Override
    public boolean deleteRecipe(int id) {
        if (RECIPE_BOOK.containsKey(id)) {
            RECIPE_BOOK.remove(id);
            saveToFile();
            return true;
        }
        return false;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(RECIPE_BOOK);
            filesService.saveToRecipesFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        String json = filesService.readFromRecipesFile();
        try {
            RECIPE_BOOK = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
