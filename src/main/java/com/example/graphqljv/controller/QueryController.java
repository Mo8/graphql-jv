package com.example.graphqljv.controller;

import com.example.graphqljv.model.*;
import com.example.graphqljv.repository.EditorRepository;
import com.example.graphqljv.repository.GameRepository;
import com.example.graphqljv.repository.StudioRepository;
import com.example.graphqljv.specification.SpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class QueryController {

    GameRepository gameRepository;
    StudioRepository studioRepository;
    EditorRepository editorRepository;

    public QueryController(GameRepository gameRepository, StudioRepository studioRepository, EditorRepository editorRepository) {
        this.gameRepository = gameRepository;
        this.studioRepository = studioRepository;
        this.editorRepository = editorRepository;
    }

    @QueryMapping
    public Games games(@Argument Integer page, @Argument String genre, @Argument String platform, @Argument String studio) {
//        Page<Game> p =  gameRepository.findByGenreAndPlatformAndStudio(genre, platform, studio, Pageable.ofSize(15).withPage(page));
//
//        return new Games(new Infos(Math.toIntExact(p.getTotalElements()), p.getTotalPages(), page + 1, page - 1), p.getContent());
        System.out.println("genre: " + genre);
        System.out.println("platform: " + platform);
        System.out.println("studio: " + studio);
        int iPage = page == null ? 0 : page;
        Page<Game> p = gameRepository.findAll(SpecificationBuilder.filterByGenreAndByPlatformAndByStudio(genre,platform,studio), Pageable.ofSize(15).withPage(iPage));
        return new Games(new Infos(Math.toIntExact(p.getTotalElements()), p.getTotalPages(), iPage + 1, iPage - 1), p.getContent());
    }

    @QueryMapping
    public Game game(@Argument String id) {
        return gameRepository.findById(Long.parseLong(id)).orElse(null);
    }

    @QueryMapping
    public Studios studios(@Argument Integer page) {
        int iPage = page == null ? 0 : page;
        Page<Studio> p = studioRepository.findAll(Pageable.ofSize(15).withPage(iPage));
        return new Studios(new Infos(Math.toIntExact(p.getTotalElements()), p.getTotalPages(), iPage + 1, iPage - 1), p.getContent());
    }

    @QueryMapping
    public Studio studio(@Argument String id) {
        return studioRepository.findById(Long.parseLong(id)).orElse(null);
    }

    @QueryMapping
    public Editors editors(@Argument Integer page) {
        int iPage = page == null ? 0 : page;
        Page<Editor> p = editorRepository.findAll(Pageable.ofSize(15).withPage(iPage));
        return new Editors(new Infos(Math.toIntExact(p.getTotalElements()), p.getTotalPages(), iPage + 1, iPage - 1), p.getContent());
    }

    @QueryMapping
    public Editor editor(@Argument String id) {
        return editorRepository.findById(Long.parseLong(id)).orElse(null);
    }

    @SchemaMapping
    public List<Editor> editors(Game game) {
        return game.getEditors();
    }

    @SchemaMapping
    public List<Studio> studios(Game game) {
        return game.getStudios();
    }

    @SchemaMapping
    public List<Game> games(Studio studio) {
        return studio.getGames();
    }

    @SchemaMapping
    public List<Game> games(Editor editor) {
        return editor.getGames();
    }
}
