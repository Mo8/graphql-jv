package com.example.graphqljv.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Editor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    @ManyToMany
    @JoinTable(name = "game_editor", joinColumns = @JoinColumn(name = "editor_id"), inverseJoinColumns = @JoinColumn(name = "game_id"))
    List<Game> games;

    public Long getId() {
        return id;
    }

    public void setId(Long ID) {
        this.id = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
