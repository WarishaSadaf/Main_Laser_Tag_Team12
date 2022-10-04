package com.example.project;
import java.util.*;
import java.lang.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Scene2Controller  implements Initializable
{
    PlayerPersistenceInterface persistenceHandler = PersistenceHandler.getInstance();

    private Stage stage;

    private Scene scene;

    private Parent root;

    @FXML
    private TextField red_id1;

    @FXML
    private TextField red_id2;


    @FXML
    private TextField red_name1;

    @FXML
    private TextField red_name2;

    @FXML
    private Label welcomeText;

    @FXML
    void addContinue(ActionEvent event) throws  IOException{
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

        System.setProperty("javafx.preloader", HelloPreloader.class.getCanonicalName());

        Parent root = FXMLLoader.load(getClass().getResource("GameAction.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);

        stage.setScene(scene);

        stage.show();

        updateUI();
    }

    private void updateUI(){
        red_id1.clear();
        red_name1.clear();
        red_id2.clear();
        red_name2.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Scene2Controller() {
    }

    @FXML

    public void EditGame (ActionEvent event) throws IOException
    {

    }
    public void StartGame (ActionEvent event) throws IOException
    {
//        System.setProperty("javafx.preloader", HelloPreloader.class.getCanonicalName());
//
//        Parent root = FXMLLoader.load(getClass().getResource("GameAction.fxml"));
//
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//
//        scene = new Scene(root);
//
//        stage.setScene(scene);
//
//        stage.show();
    }
    public void ViewGame (ActionEvent event) throws IOException
    {

    }
    public void PreEnteredGames (ActionEvent event) throws IOException
    {

    }
    public void FlickSync (ActionEvent event) throws IOException
    {

    }

    public void ClearGame (ActionEvent event) throws IOException
    {

    }

    public class Controller
    {
        public void move_up ()
        {

        }
        public void move_down()
        {

        }
        public void move_left ()
        {

        }
        public void move_right()
        {

        }
    }
    public void closeGame(ActionEvent event) throws IOException
    {
        System.exit(0);

    }

}
