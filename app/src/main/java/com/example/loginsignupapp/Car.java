package com.example.loginsignupapp;

import java.io.Serializable;


public class Car implements Serializable {
    private String name;
    private String history;
    private String description;
    private CarCategory category;
    private String photo;

    public Car() {
    }

    public Car(String name, String history, String description, CarCategory category, String photo) {
        this.name = name;
        this.history = history;
        this.description = description;
        this.category = category;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CarCategory getCategory() {
        return category;
    }

    public void setCategory(CarCategory category) {
        this.category = category;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", history='" + history + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", photo='" + photo + '\'' +
                '}';
    }
}