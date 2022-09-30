package com.example.project;

public class Player
{
    private String id;
    private String codename;

    public Player(String id, String codename) {
        this.id = id;
        this.codename = codename;
    }

    public String getId() {
        return id;
    }

    public String getCodename() {
        return codename;
    }

    @Override
    public String toString() {
        return " with ID: " + id + " got codename: " + codename;
    }
}
