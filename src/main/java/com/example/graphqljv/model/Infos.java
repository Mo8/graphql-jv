package com.example.graphqljv.model;

public class Infos {
    int count;
    int pages;
    int nextPage;
    int previousPage;

    public Infos(int count, int pages, int nextPage, int previousPage) {
        this.count = count;
        this.pages = pages;
        this.nextPage = Math.min(nextPage, pages);
        this.previousPage = Math.max(previousPage, 0);
    }
}
