package me.gorbunov.recipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.gorbunov.recipesapp.model.Ingredient;
import me.gorbunov.recipesapp.services.IngredientBook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredients")
@Tag(name = "Ингредиенты", description = "CRUD-операции с ингредиентами")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Запрос выполнен успешно.", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "400", description = "Ошибка в параметрах запроса.", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", description = "URL неверный или такого действия нет в веб-приложении.", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", description = "Во время выполнения запроса произошла ошибка на сервере.", content = {@Content(mediaType = "application/json")})
})
public class IngredientController {

    private final IngredientBook ingredientBook;

    public IngredientController(IngredientBook ingredientBook) {
        this.ingredientBook = ingredientBook;
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Поиск ингредиента по id",
            description = "Введите id ингредиента и получите полные сведения по нему"
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
                            description = "Ингредиент был найден",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Ingredient.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ингредиента по такому id не существует",
                            content = {
                                    @Content(
                                            mediaType = "application/json"
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Integer id) {
        Ingredient ingredient = ingredientBook.getIngredient(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Замена ингредиента по id",
            description = "Введите id ингредиента, который хотите изменить и введите новый"
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
                            description = "Ингредиент был найден и изменён",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Ingredient.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ингредиента по такому id не существует",
                            content = {
                                    @Content(
                                            mediaType = "application/json"
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable Integer id, @RequestBody Ingredient ingredient) {
        Ingredient newIngredient = ingredientBook.updateIngredient(id, ingredient);
        if (newIngredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newIngredient);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление ингредиента по id",
            description = "Введите id ингредиента, который хотите удалить"
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
                            description = "Ингредиент был найден и удалён",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Integer.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ингредиента по такому id не существует",
                            content = {
                                    @Content(
                                            mediaType = "application/json"
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Integer> deleteIngredient(@PathVariable Integer id) {
        boolean delete = ingredientBook.deleteIngredient(id);
        if (!delete) {
            return ResponseEntity.notFound().build();
        }
        ingredientBook.deleteIngredient(id);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/")
    @Operation(
            summary = "Выдача всех ингредиентов",
            description = "Выводит все содержащиеся в приложении ингредиенты"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ингредиенты были найдены и показаны",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Map<Integer, Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientBook.getAllIngredients());
    }

    @PostMapping("/")
    @Operation(
            summary = "Добавление нового ингредиента",
            description = "Введите данные ингредиента, который хотите добавить"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ингредиент был добавлен",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Integer.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Integer> addIngredient(@RequestBody Ingredient ingredient) {
        Integer id = ingredientBook.addIngredient(ingredient);
        return ResponseEntity.ok(id);
    }
}
