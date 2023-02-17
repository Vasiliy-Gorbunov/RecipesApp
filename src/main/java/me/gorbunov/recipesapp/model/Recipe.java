package me.gorbunov.recipesapp.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

@Data
public class Recipe {
    private String name;
    private int cookingTime;
    private List<Ingredient> ingredients;
    private List<String> cookSteps;


    public Recipe(String name, int cookingTime, List<Ingredient> ingredients, LinkedList<String> cookSteps) {
        setName(name);
        setCookingTime(cookingTime);
        this.ingredients = ingredients;
        this.cookSteps = cookSteps;
    }


    public void setName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Поле \"name\" должно быть заполнено");
        }
        this.name = name;
    }

    public void setCookingTime(int cookingTime) {
        if (cookingTime <= 0) {
            throw new IllegalArgumentException("Поле \"cookingTime\" не может быть неположительным числом");
        }
        this.cookingTime = cookingTime;
    }

}
