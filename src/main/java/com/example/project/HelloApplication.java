package com.example.project;
import java.util.*;
import java.lang.*;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application 
{
    private static final int COUNT_LIMIT = 10;
    @Override
    public void start(Stage stage) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        //stage.setOpacity();
        stage.show();
    }

    @Override
    public void init() throws Exception
    {
        for (int i = 0; i < COUNT_LIMIT; i++)
        {
            double progress = (double) (i) / COUNT_LIMIT;
            //System.out.println("Progress: " + progress);
            notifyPreloader(new Preloader.ProgressNotification(progress));
            Thread.sleep(300);
        }
    }


    public static void main(String[] args)
    {
        System.setProperty("javafx.preloader", HelloPreloader.class.getCanonicalName());
        launch(args);
    }
}
