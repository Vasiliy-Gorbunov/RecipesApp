package me.gorbunov.recipesapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.gorbunov.recipesapp.model.Ingredient;
import me.gorbunov.recipesapp.model.Recipe;
import me.gorbunov.recipesapp.services.FilesService;
import me.gorbunov.recipesapp.services.RecipeBook;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
    public Path getRecipesAsText() throws IOException {
        Path path = filesService.createTempFile();
        try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)){
            for (Recipe recipe : RECIPE_BOOK.values()) {
                writer.append(recipe.getName()).append("\n\n")
                        .append("Время приготовления: ").append(String.valueOf(recipe.getCookingTime())).append(" минут.\n\n")
                        .append("Ингредиенты:\n\n");
                for (Ingredient ingredient : recipe.getIngredients()) {
                    writer.append(ingredient.getName()).append(" - ").append(String.valueOf(ingredient.getCount())).append(" ").append(ingredient.getMeasureUnit()).append("\n");
                }
                    writer.append("\nИнструкция приготовления:\n\n");
                for (String cookStep : recipe.getCookSteps()) {
                    writer.append(cookStep).append("\n");
                }
                writer.append("\n\n");
            }
        }
        return path;
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

    @Override
    public void readFromFile() {
        String json = filesService.readFromRecipesFile();
        try {
            RECIPE_BOOK = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
