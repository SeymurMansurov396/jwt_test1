package com.asan.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "security_roles")
public class ApplicationUserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Collection<ApplicationUser> users;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "ApplicationUserRole{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", users=" + users +
                '}';
    }
}
