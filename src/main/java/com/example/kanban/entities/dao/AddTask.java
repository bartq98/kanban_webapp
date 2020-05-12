package com.example.kanban.entities.dao;

public class AddTask {
    private String title;
    private String description;
    private Integer column;

    public AddTask(String title, String description, Integer column) {
        this.title = title;
        this.description = description;
        this.column = column;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }
}
