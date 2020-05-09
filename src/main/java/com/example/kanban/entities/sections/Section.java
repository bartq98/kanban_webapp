package com.example.kanban.entities.sections;

import com.example.kanban.entities.boards.Board;
import com.example.kanban.entities.task.Task;

import javax.persistence.*;
import java.util.Set;

@Entity // Or Section
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Board board;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private ColorType color;

    @Column(nullable = false, updatable = false)
    private Integer ordering;

    @OneToMany(mappedBy = "section")
    private Set<Task> tasks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Board getBoard() { return board; }

    public void setBoard(Board board) { this.board = board; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ColorType getColor() {
        return color;
    }

    public void setColor(ColorType color) {
        this.color = color;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public Set<Task> getTasks() { return tasks; }

    public void setTasks(Set<Task> tasks) { this.tasks = tasks; }
}