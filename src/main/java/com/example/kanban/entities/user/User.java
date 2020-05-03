package com.example.kanban.entities.user;

import com.example.kanban.entities.membership.Membership;
import com.example.kanban.entities.task.Task;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty()
    @Size(max = 30)
    @Column(unique = true, nullable = false)
    private String userName;

    @Enumerated(EnumType.STRING)
    private UserType roles = UserType.USER;

    @NotEmpty()
    @Size(max = 30)
    @Column(unique = true, nullable = true)
    private String email;

    @NotEmpty()
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String name;

    @NotEmpty()
    @Size(max = 30)
    @Column(nullable = true, length = 30)
    private String surname;

    @NotEmpty()
    @Size(min = 6)
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Membership> memberships;

    @OneToMany(mappedBy = "user")
    private Set<Task> tasks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(userName, user.userName) &&
                roles == user.roles &&
                Objects.equals(email, user.email) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(password, user.password) &&
                Objects.equals(memberships, user.memberships) &&
                Objects.equals(tasks, user.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoles() {
        return roles.getAuthority();
    }

    public void setRoles(UserType roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
