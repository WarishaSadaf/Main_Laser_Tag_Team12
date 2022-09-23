package com.example.project;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HelloPreloader extends Preloader {

    //ProgressBar bar;
    private Stage preloaderStage;
    private Scene scene;

    /*private Scene createPreloaderScene() {
        bar = new ProgressBar();
        BorderPane p = new BorderPane();
        p.setCenter(bar);
        return new Scene(p, 300, 150);
    }*/

    @Override
    public void init() throws Exception {
        Parent root1 = FXMLLoader.load(getClass().getResource("splash_hello-view.fxml"));
        scene = new Scene(root1);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.preloaderStage = stage;
        //Set preloader scene and show stage
        preloaderStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.show();
    }

    @Override
    public void handleApplicationNotification(Preloader.PreloaderNotification info) {
        //bar.setProgress(pn.getProgress());
        if(info instanceof ProgressNotification){
            HelloController.label.setText("Loading....."+((ProgressNotification)info).getProgress()*100 + "%");
            System.out.println("Value@ :"+ (((ProgressNotification) info).getProgress()));
            HelloController.statProgressBar.setProgress(((ProgressNotification)info).getProgress());

        }
    }

    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            preloaderStage.hide();
        }
    }
}
