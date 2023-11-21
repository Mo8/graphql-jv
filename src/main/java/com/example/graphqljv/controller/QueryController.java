package com.example.graphqljv.controller;

import com.example.graphqljv.model.*;
import com.example.graphqljv.repository.EditorRepository;
import com.example.graphqljv.repository.GameRepository;
import com.example.graphqljv.repository.StudioRepository;
import com.example.graphqljv.specification.SpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
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
        int iPage = page == null ? 0 : page;
        Page<Game> p = gameRepository.findAll(SpecificationBuilder.filterByGenreAndByPlatformAndByStudio(genre, platform, studio), Pageable.ofSize(15).withPage(iPage));
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

    @MutationMapping
    public Game addGame(@Argument String name, @Argument List<String> genres, @Argument int publicationDate, @Argument List<String> platforms, @Argument List<Long> editors, @Argument List<Long> studios) {
        Game game = new Game();
        game.setName(name);
        game.setGenres(genres);
        game.setPublicationDate(publicationDate);
        game.setPlatforms(platforms);
        game.setEditors(editorRepository.findAllById(editors));
        game.setStudios(studioRepository.findAllById(studios));
        return gameRepository.save(game);
    }

    @MutationMapping
    public Game updateGame(@Argument String id, @Argument String name, @Argument List<String> genres, @Argument Integer publicationDate, @Argument List<String> platforms, @Argument List<Long> editors, @Argument List<Long> studios) {
        Game game = gameRepository.findById(Long.parseLong(id)).orElse(null);
        assert game != null;
        if (name != null)
            game.setName(name);
        if (genres != null)
            game.setGenres(genres);
        if (publicationDate != null)
            game.setPublicationDate(publicationDate);
        if (platforms != null)
            game.setPlatforms(platforms);
        if (editors != null)
            game.setEditors(editorRepository.findAllById(editors));
        if (studios != null)
            game.setStudios(studioRepository.findAllById(studios));
        return gameRepository.save(game);
    }

    @MutationMapping
    public Game deleteGame(@Argument String id) {
        Game game = gameRepository.findById(Long.parseLong(id)).orElse(null);
        assert game != null;
        gameRepository.delete(game);
        return game;
    }

    @MutationMapping
    public Studio addStudio(@Argument String name) {
        Studio studio = new Studio();
        studio.setName(name);
        return studioRepository.save(studio);
    }

    @MutationMapping
    public Studio updateStudio(@Argument String id, @Argument String name) {
        Studio studio = studioRepository.findById(Long.parseLong(id)).orElse(null);
        assert studio != null;
        studio.setName(name);
        return studioRepository.save(studio);
    }

    @MutationMapping
    public Studio deleteStudio(@Argument String id) {
        Studio studio = studioRepository.findById(Long.parseLong(id)).orElse(null);
        assert studio != null;
        studioRepository.delete(studio);
        return studio;
    }

    @MutationMapping
    public Editor addEditor(@Argument String name) {
        Editor editor = new Editor();
        editor.setName(name);
        return editorRepository.save(editor);
    }

    @MutationMapping
    public Editor updateEditor(@Argument String id, @Argument String name) {
        Editor editor = editorRepository.findById(Long.parseLong(id)).orElse(null);
        assert editor != null;
        editor.setName(name);
        return editorRepository.save(editor);
    }

    @MutationMapping
    public Editor deleteEditor(@Argument String id) {
        Editor editor = editorRepository.findById(Long.parseLong(id)).orElse(null);
        assert editor != null;
        editorRepository.delete(editor);
        return editor;
    }
}
