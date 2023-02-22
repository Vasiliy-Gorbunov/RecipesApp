package me.gorbunov.recipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.gorbunov.recipesapp.services.FilesService;
import me.gorbunov.recipesapp.services.IngredientBook;
import me.gorbunov.recipesapp.services.RecipeBook;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/files")
@Tag(name = "Файлы", description = "Импорт/экспорт файлов")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Операция с файлом прошла успешно.", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "400", description = "Ошибка в параметрах запроса.", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", description = "URL неверный или такого действия нет в веб-приложении.", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", description = "Во время выполнения запроса произошла ошибка на сервере.", content = {@Content(mediaType = "application/json")})
})
public class FilesController {
    private final FilesService filesService;
    private final RecipeBook recipeBook;
    private final IngredientBook ingredientBook;

    public FilesController(FilesService filesService, RecipeBook recipeBook, IngredientBook ingredientBook) {
        this.filesService = filesService;
        this.recipeBook = recipeBook;
        this.ingredientBook = ingredientBook;
    }

    @GetMapping("/export/recipes")
    @Operation(
            summary = "Формирование файла с рецептами",
            description = "Позволяет скачать .json файл со всеми рецептами"
    )
    public ResponseEntity<InputStreamResource> downloadRecipesFile() throws FileNotFoundException {
        File file = filesService.getRecipesFile();

        if (!file.exists()) {
            return ResponseEntity.noContent().build();
        }
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(file.length())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipesBook.json\"")
                .body(resource);
    }

    @GetMapping("/export/recipes/text")
    @Operation(
            summary = "Формирование текстового файла с рецептами",
            description = "Позволяет скачать текстовый файл со всеми рецептами"
    )
    public ResponseEntity<Object> downloadRecipesTextFile() {
        try {
            Path path = recipeBook.getRecipesAsText();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipesBook.txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.getStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }


    }

    @PostMapping(value = "/import/recipes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузка своего файла с рецептами",
            description = "Позволяет загрузить .json файл со своими рецептами в приложение"
    )
    public ResponseEntity<Void> uploadRecipesFile(@RequestParam MultipartFile file) {
        if (!StringUtils.equals(file.getContentType(), "application/json")) {
            return ResponseEntity.badRequest().build();
        }
        filesService.cleanDataFile(filesService.getRecipesFile().getName());
        File recipesFile = filesService.getRecipesFile();
        try (FileOutputStream fos = new FileOutputStream(recipesFile)) {
            fos.write(file.getInputStream().readAllBytes());
            recipeBook.readFromFile();
            return ResponseEntity.ok().build();
        } catch (
                IOException e) {
            e.getStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }



    @PostMapping(value = "/import/ingredients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузка своего файла с ингредиентами",
            description = "Позволяет загрузить .json файл со своими ингредиентами в приложение"
    )
    public ResponseEntity<Void> uploadIngredientsFile(@RequestParam MultipartFile file) {
        if (!StringUtils.equals(file.getContentType(), "application/json")) {
            return ResponseEntity.badRequest().build();
        }
        filesService.cleanDataFile(filesService.getIngredientsFile().getName());
        File ingredientsFile = filesService.getIngredientsFile();
        try (FileOutputStream fos = new FileOutputStream(ingredientsFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            ingredientBook.readFromFile();
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.getStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
