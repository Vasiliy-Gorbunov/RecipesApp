package me.gorbunov.recipesapp.services;

public interface FilesService {
    void saveToRecipesFile(String json);

    default void saveToIngredientsFile(String json) {
    }

    String readFromRecipesFile();

    String readFromIngredientsFile();
}
