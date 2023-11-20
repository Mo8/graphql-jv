package com.example.graphqljv.model;

import java.util.List;

public class Games {
    Infos infos;


    List<Game> results;

    public Games(Infos infos, List<Game> results) {
        this.infos = infos;
        this.results = results;
    }
}
