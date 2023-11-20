package com.example.graphqljv.model;

import java.util.List;

public class Studios {
    Infos infos;
    List<Studio> results;

    public Studios(Infos infos, List<Studio> results) {
        this.infos = infos;
        this.results = results;
    }
}
