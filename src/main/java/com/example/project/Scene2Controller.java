package com.example.project;

import com.example.domain.Player;
import com.example.domain.PlayerPersistenceInterface;
import com.example.persistence.PersistenceHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Scene2Controller implements Initializable {

    PlayerPersistenceInterface persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    private TextField red_id1;

    @FXML
    private TextField red_id2;


    @FXML
    private TextField red_name1;

    @FXML
    private TextField red_name2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void addContinue(ActionEvent event) {
        Player player1 = new Player((red_id1.getText()), red_name1.getText());
        Player player2 = new Player((red_id2.getText()), red_name2.getText());
        if(persistenceHandler.createPlayer(player1)){
            System.out.println("Player inserted into database!!");
        } else {
            System.out.println("Error while inserting player into database!!");
        }
        if(persistenceHandler.createPlayer(player2)){
            System.out.println("Player inserted into database!!");
        } else {
            System.out.println("Error while inserting player into database!!");
        }
        updateUI();
    }

    private void updateUI(){
        red_id1.clear();
        red_name1.clear();
        red_id2.clear();
        red_name2.clear();
    }

}
