package me.gorbunov.recipesapp.services;

import java.io.File;
import java.nio.file.Path;

public interface FilesService {
    void saveToRecipesFile(String json);

    void saveToIngredientsFile(String json);

    String readFromRecipesFile();

    String readFromIngredientsFile();

    void cleanDataFile(String fileName);

    File getRecipesFile();

    File getIngredientsFile();

    Path createTempFile();
}
