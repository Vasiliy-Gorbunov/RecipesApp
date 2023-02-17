package me.gorbunov.recipesapp.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class Ingredient {

    private String name;
    private int count;
    private String measureUnit;


    public Ingredient(String name, int count, String measureUnit) {
        setName(name);
        setCount(count);
        setMeasureUnit(measureUnit);
    }


    public void setName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Поле \"name\" должно быть заполнено");
        }
        this.name = name;
    }

    public void setCount(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Поле \"count\" не может быть неположительным числом");
        }
        this.count = count;
    }

    public void setMeasureUnit(String measureUnit) {
        if (StringUtils.isBlank(measureUnit)) {
            throw new IllegalArgumentException("Поле \"measureUnit\" должно быть заполнено");
        }
        this.measureUnit = measureUnit;
    }
}
