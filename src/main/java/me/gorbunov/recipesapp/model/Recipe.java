package me.gorbunov.recipesapp.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class Recipe {
    private String name;
    private int cookingTime;
    private List<Ingredient> ingredients;
    private LinkedList<String> cookSteps;


    public Recipe(String name, int cookingTime, List<Ingredient> ingredients, LinkedList<String> cookSteps) {
        setName(name);
        setCookingTime(cookingTime);
        this.ingredients = ingredients;
        this.cookSteps = cookSteps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Поле \"name\" должно быть заполнено");
        }
        this.name = name;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        if (cookingTime <= 0) {
            throw new IllegalArgumentException("Поле \"cookingTime\" не может быть неположительным числом");
        }
        this.cookingTime = cookingTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public LinkedList<String> getCookSteps() {
        return cookSteps;
    }

    public void setCookSteps(LinkedList<String> cookSteps) {
        this.cookSteps = cookSteps;
    }
}
