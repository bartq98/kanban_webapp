package com.example.kanban.entities.dao;

public class MovedTask {
    private Integer taskId;
    private Integer oldColumn;
    private Integer newColumn;

    public MovedTask(Integer taskId, Integer oldColumn, Integer newColumn) {
        this.taskId = taskId;
        this.oldColumn = oldColumn;
        this.newColumn = newColumn;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getOldColumn() {
        return oldColumn;
    }

    public void setOldColumn(Integer oldColumn) {
        this.oldColumn = oldColumn;
    }

    public Integer getNewColumn() {
        return newColumn;
    }

    public void setNewColumn(Integer newColumn) {
        this.newColumn = newColumn;
    }
}
