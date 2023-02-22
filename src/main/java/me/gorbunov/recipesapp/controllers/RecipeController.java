package me.gorbunov.recipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.gorbunov.recipesapp.model.Recipe;
import me.gorbunov.recipesapp.services.RecipeBook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipes")
@Tag(name = "Рецепты", description = "CRUD-операции с рецептами")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Запрос выполнен успешно.", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "400", description = "Ошибка в параметрах запроса.", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", description = "URL неверный или такого действия нет в веб-приложении.", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", description = "Во время выполнения запроса произошла ошибка на сервере.", content = {@Content(mediaType = "application/json")})
})
public class RecipeController {

    private final RecipeBook recipeBook;

    public RecipeController(RecipeBook recipeBook) {
        this.recipeBook = recipeBook;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Поиск рецепта по id",
            description = "Введите id рецепта и получите полные сведения по нему"
    )
    @Parameter(
            name = "id",
            description = "Целочисленное значение от 0",
            example = "0"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Рецепт был найден",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Recipe.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Рецепта по такому id не существует",
                            content = {
                                    @Content(
                                            mediaType = "application/json"
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Integer id) {
        Recipe recipe = recipeBook.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Замена рецепта по id",
            description = "Введите id рецепта, который хотите изменить и введите новый"
    )
    @Parameter(
            name = "id",
            description = "Целочисленное значение от 0",
            example = "0"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Рецепт был найден и изменён",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Recipe.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Рецепта по такому id не существует",
                            content = {
                                    @Content(
                                            mediaType = "application/json"
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Integer id, @RequestBody Recipe recipe) {
        Recipe newRecipe = recipeBook.updateRecipe(id, recipe);
        if (newRecipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newRecipe);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление рецепта по id",
            description = "Введите id рецепта, который хотите удалить"
    )
    @Parameter(
            name = "id",
            description = "Целочисленное значение от 0",
            example = "0"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Рецепт был найден и удалён",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Integer.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Рецепта по такому id не существует",
                            content = {
                                    @Content(
                                            mediaType = "application/json"
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Integer> deleteRecipe(@PathVariable Integer id) {
        boolean delete = recipeBook.deleteRecipe(id);
        if (!delete) {
            return ResponseEntity.notFound().build();
        }
        recipeBook.deleteRecipe(id);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/")
    @Operation(
            summary = "Выдача всех рецептов",
            description = "Выводит все содержащиеся в приложении рецепты"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Рецепт были найдены и показаны",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Map<Integer, Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeBook.getAllRecipes());
    }

    @PostMapping("/")
    @Operation(
            summary = "Добавление нового рецепта",
            description = "Введите данные рецепта, который хотите добавить"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Рецепт был добавлен",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Integer.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Integer> addRecipe(@RequestBody Recipe recipe) {
        Integer id = recipeBook.addNewRecipe(recipe);
        return ResponseEntity.ok(id);
    }
}
