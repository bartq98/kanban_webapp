package com.example.kanban.entities.boards;

import com.example.kanban.entities.membership.Membership;
import com.example.kanban.entities.sections.Section;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Boards")
public class Board {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;

    @Column(nullable = false, length = 255, unique = true)
    private String slug;

    @OneToMany(mappedBy = "board")
    private Set<Section> sections;

    @OneToMany(mappedBy = "board")
    private Set<Membership> memberships;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return getId().equals(board.getId()) &&
                Objects.equals(getName(), board.getName()) &&
                Objects.equals(getCreated_at(), board.getCreated_at()) &&
                Objects.equals(getSlug(), board.getSlug()) &&
                Objects.equals(sections, board.sections) &&
                Objects.equals(memberships, board.memberships);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
