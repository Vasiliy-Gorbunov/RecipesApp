package me.gorbunov.recipesapp.services.impl;

import me.gorbunov.recipesapp.services.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {

    @Value("${path.to.files.folder}")
    private String pathToFileFolder;

    @Value("${name.of.recipes.file}")
    private String nameOfRecipesFile;

    @Value("${name.of.ingredients.file}")
    private String nameOfIngredientsFile;


    private void saveToDataFile(String fileName, String json) throws IOException {
        cleanDataFile(fileName);
        Files.writeString(Path.of(pathToFileFolder, fileName), json);
    }

    @Override
    public void saveToRecipesFile(String json) {
        try {
            saveToDataFile(nameOfRecipesFile, json);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void saveToIngredientsFile(String json) {
        try {
            saveToDataFile(nameOfIngredientsFile, json);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    private String readFromDataFile(String fileName) throws IOException {
        Path path = Path.of(pathToFileFolder, fileName);
        if (Files.exists(path)) {
            return Files.readString(path);
        }
        return "{}";
    }

    @Override
    public String readFromRecipesFile() {
        try {
            return readFromDataFile(nameOfRecipesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String readFromIngredientsFile() {
        try {
            return readFromDataFile(nameOfIngredientsFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cleanDataFile(String fileName) {
        try {
            Path path = Path.of(pathToFileFolder, fileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    private File getDataFile(String fileName) {
        return new File(pathToFileFolder + "/" + fileName);
    }

    @Override
    public File getRecipesFile() {
        return getDataFile(nameOfRecipesFile);
    }

    @Override
    public File getIngredientsFile() {
        return getDataFile(nameOfIngredientsFile);
    }

    @Override
    public Path createTempFile() {
        try {
            return Files.createTempFile(Path.of(pathToFileFolder), "tmp", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
