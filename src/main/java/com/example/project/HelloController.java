/*
    This is the first controller that control the hello application
 */

package com.example.project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable
{

    private Stage stage;

    private Scene scene;

    public HelloController()
    {

    }
    @FXML
    public Label progress;

    public static Label label;

    @FXML
    public ProgressBar progressBar;

    public static ProgressBar statProgressBar;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        label = progress;
        statProgressBar = progressBar;


    }


    @FXML
    public void switchToScene2(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);

        stage.setScene(scene);

        stage.show();


    }

    public void closeGame(ActionEvent event) throws IOException
    {
        System.exit(0);
    }

}