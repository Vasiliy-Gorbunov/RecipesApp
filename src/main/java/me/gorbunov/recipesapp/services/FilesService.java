package me.gorbunov.recipesapp.services;

public interface FilesService {
    void saveToRecipesFile(String json);

    void saveToIngredientsFile(String json);

    String readFromRecipesFile();

    String readFromIngredientsFile();
}
