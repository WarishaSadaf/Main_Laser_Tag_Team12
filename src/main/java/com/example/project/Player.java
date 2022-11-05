package com.example.project;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Player {

    //private String id;
    //private String codename;

    private IntegerProperty id;
    private StringProperty codename;


    /*public Player(int id, String codename) {
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
    }*/
    public Player() {
        this.id = new SimpleIntegerProperty();
        this.codename = new SimpleStringProperty();
    }

    //player_id
    public int getId() {
        return id.get();
    }
    public void setId(int id){
        this.id.set(id);
    }
    public IntegerProperty idProperty(){
        return id;
    }
    //Player_Codename
    public String getCodename () {
        return codename.get();
    }
    public void setCodename(String codename){
        this.codename.set(codename);
    }
    public StringProperty codenameProperty() {
        return codename;
    }
}

