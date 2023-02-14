package me.gorbunov.recipesapp.model;

import lombok.Data;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Поле \"name\" должно быть заполнено");
        }
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Поле \"count\" не может быть неположительным числом");
        }
        this.count = count;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        if (measureUnit == null || measureUnit.isEmpty() || measureUnit.isBlank()) {
            throw new IllegalArgumentException("Поле \"measureUnit\" должно быть заполнено");
        }
        this.measureUnit = measureUnit;
    }
}
