package com.example.kanban.entities.sections;

public enum ColorType {
    BLUE_BASIC("#64b5f6"),
    BLUE_LIGHT("#9be7ff"),
    BLUE_DARK("#2286c3"),
    ORANGE_BASIC("#ff9800");

    private String color;

    ColorType(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
