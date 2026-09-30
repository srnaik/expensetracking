package com.sac.expensetracking.models;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable=false, columnDefinition = "UUID")
    private User user;

    @Column(name = "name", nullable=false, columnDefinition = "CHARACTER VARYING")
    private String name;

    @Column(name = "type", nullable=false, columnDefinition = "CHARACTER VARYING")
    private String type;

    @Column(name = "icon", nullable=false, columnDefinition = "CHARACTER VARYING")
    private String icon;

    @Column(name = "color", nullable=false, columnDefinition = "CHARACTER VARYING")
    private String color;


    public Category(){

    }

    public Category(UUID id, User user, String name, String type, String icon, String color) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.type = type;
        this.icon = icon;
        this.color = color;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
