package com.example.graphqljv.model;

import java.util.List;

public class Editors {
    Infos infos;
    List<Editor> results;

    public Editors(Infos infos, List<Editor> results) {
        this.infos = infos;
        this.results = results;
    }
}
