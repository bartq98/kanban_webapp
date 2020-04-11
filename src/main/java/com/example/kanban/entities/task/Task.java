package com.example.kanban.entities.task;

import com.example.kanban.entities.sections.Section;
import com.example.kanban.entities.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tasks")
public class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Section section;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private Integer executive_id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime expires_at;

    @Column(name = "task_title", nullable = false, length = 255)
    private String title;

    @Column(name = "task_description")
    private String description;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getColumn_id() {
        return section.getId();
    }

    public void setColumn_id(Integer column_id) {
        this.section.getId();
    }

    public Integer getExecutive_id() {
        return executive_id;
    }

    public void setExecutive_id(Integer executive_id) {
        this.executive_id = executive_id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at() {
        this.created_at = LocalDateTime.now();
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(LocalDateTime expires_at) {
        this.expires_at = expires_at;
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
}