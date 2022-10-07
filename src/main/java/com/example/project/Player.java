/*
* Overview: implementation for the Player class. This class is used to
*           initialize and retrieve a player's object information (i.e. player ID and name).
* @version 1.0
* @since 09/09/22
* */
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
