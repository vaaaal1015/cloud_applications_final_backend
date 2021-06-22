package com.example.demo.entity;

public class Paper {
    private int id;
    private int select;

    public Paper() {

    }

    public Paper(int id, int select) {
        this.id = id;
        this.select = select;
    }

    public int getId() {
        return id;
    }

    public int getSelect() {
        return select;
    }

}
