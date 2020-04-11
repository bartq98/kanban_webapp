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
    private String name;

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

    public Integer getBoard_id() {
        return board.getId();
    }

    public void setBoard_id(Integer board_id) {
        this.board.setId(board_id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}