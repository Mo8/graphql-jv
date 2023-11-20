package com.example.graphqljv.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    @ElementCollection
    List<String> genres;
    int publicationDate;
    @ManyToMany
            @JoinTable(
                    name = "game_editor",
                    joinColumns = @JoinColumn(name = "game_id"),
                    inverseJoinColumns = @JoinColumn(name = "editor_id")
            )
    List<Editor> editors;
    @ManyToMany
            @JoinTable(
                    name = "game_studio",
                    joinColumns = @JoinColumn(name = "game_id"),
                    inverseJoinColumns = @JoinColumn(name = "studio_id")
            )
    List<Studio> studios;
    @ElementCollection
    List<String> platforms;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getGenre() {
        return genres;
    }

    public void setGenre(List<String> genres) {
        this.genres = genres;
    }

    public int getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(int publicationDate) {
        this.publicationDate = publicationDate;
    }

    public List<Editor> getEditors() {
        return editors;
    }

    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }

    public List<Studio> getStudios() {
        return studios;
    }

    public void setStudios(List<Studio> studios) {
        this.studios = studios;
    }

    public List<String> getPlatform() {
        return platforms;
    }

    public void setPlatform(List<String> platform) {
        this.platforms = platform;
    }
}
