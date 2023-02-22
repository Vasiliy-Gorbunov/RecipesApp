package me.gorbunov.recipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@Tag(name = "Базовый контроллер", description = "Выводит стартовую страницу и страницу с информацией о приложении")
public class FirstController {
    @GetMapping("/")
    @Operation(
            summary = "Стартовая страница приложения"
    )
    @ApiResponse(
            content = {
                    @Content(
                            schema = @Schema(
                                    example = "Приложение запущено"
                            )
                    )
            }
    )
    public String startApp() {
        return "Приложение запущено";
    }


    @GetMapping("/info")
    @Operation(
            summary = "Информация о проекте",
            description = "На этой странице выводится информация о проекте"
    )
    public String getInfo() throws IOException {
        Path path = Paths.get("src/main/resources/README.md");
        List<String> readme = Files.readAllLines(path);
        StringBuilder sb = new StringBuilder();
        sb.append("Имя ученика: Василий Горбунов<br>");
        sb.append("Название проекта: \"Recipes App\"<br>");
        sb.append("Дата создания проекта: 07.02.2023г.<br><br>");
        for (String string : readme) {
            sb.append(string);
        }
        return sb.toString();
    }
}
