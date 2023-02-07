package me.gorbunov.recipesapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class FirstController {
    @GetMapping("/")
    public String startApp() {
        return "Приложение запущено";
    }

    @GetMapping("/info")
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
