package me.gorbunov.recipesapp.services;

import java.io.File;

public interface FilesService {
    void saveToRecipesFile(String json);

    void saveToIngredientsFile(String json);

    String readFromRecipesFile();

    String readFromIngredientsFile();

    void cleanDataFile(String fileName);

    File getRecipesFile();

    File getIngredientsFile();
}
