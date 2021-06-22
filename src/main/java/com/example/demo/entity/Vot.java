package com.example.demo.entity;

import java.util.List;

public class Vot {
    private int id;
    private String title;
    private List<String> option;

    public Vot() {

    }

    public Vot(int id, String title, List<String> option) {
        this.id = id;
        this.title = title;
        this.option = option;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getOption() {
        return option;
    }
}
