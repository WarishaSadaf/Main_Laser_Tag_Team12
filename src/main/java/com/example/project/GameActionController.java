package com.example.project;
<<<<<<< HEAD
import javafx.application.Platform;
=======

>>>>>>> 763f6ec0c211bf09eff1dbbcc74c608fb66e4e1c
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
<<<<<<< HEAD
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
=======
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

>>>>>>> 763f6ec0c211bf09eff1dbbcc74c608fb66e4e1c
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
<<<<<<< HEAD
import java.util.Timer;
import java.util.TimerTask;
public class GameActionController implements Initializable {

    static int i;
    static Timer t;
    String nm;

    @FXML
    private Label label;
    public static Label static_label;

=======

public class GameActionController implements Initializable {

>>>>>>> 763f6ec0c211bf09eff1dbbcc74c608fb66e4e1c
    PlayerPersistenceInterface persistenceHandler = PersistenceHandler.getInstance();

    private Stage stage;

    private Scene scene;

    private Parent root;

    @FXML
    private TableView RedTeamTable;
    @FXML
    private TableColumn<Player, Integer> redIdColumn;
    @FXML
    private TableColumn<Player, String>  redCodeNameColumn;

    @FXML
    private TableView GreenTeamTable;
    @FXML
    private TableColumn<Player, Integer> greenIdColumn;
    @FXML
    private TableColumn<Player, String>  greenCodeNameColumn;


    @FXML
    void addContinue(ActionEvent event) throws IOException {

        System.setProperty("javafx.preloader", HelloPreloader.class.getCanonicalName());

        Parent root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);

        stage.setScene(scene);

        stage.show();

        updateUI();
    }

    private void updateUI(){

    }
<<<<<<< HEAD
    public static void startTimer() {

        final int MIN = 6;
        final int SEC = 60;
        final int[] num = {MIN};
        Timer timer = new Timer();
        final int[] count = {SEC};
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                Platform.runLater
                        (new Runnable()
                         {
                             public void run()
                             {
                                 String c = String.format(num[0] +":"+"%02d",count[0]);
                                 static_label.setText(c);
                                 count[0]--;

                                 if (count[0] == 0 && num[0] !=0)
                                 {
                                     num[0]--;
                                     count[0] = SEC;
                                 }
                                 if (count[0] < 0 && num[0] == 0)
                                 {
                                     System.out.println("GAME FINISH");
                                     timer.cancel();
                                 }
                                 if (count[0] == 29 && num[0] == 0)
                                 {
                                     //ALERT: Time's running out
                                     static_label.setTextFill(Color.RED);
                                 }




                             }
                         }
                        );
            }
        }, 1000, 1000); //Every 1 second

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        static_label = label;

        static_label.setText("");

        startTimer();

=======

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
>>>>>>> 763f6ec0c211bf09eff1dbbcc74c608fb66e4e1c
        try {
            searchAllPlayers();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        redIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        redCodeNameColumn.setCellValueFactory(cellData -> cellData.getValue().codenameProperty());

        greenIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        greenCodeNameColumn.setCellValueFactory(cellData -> cellData.getValue().codenameProperty());

    }


    //Search all players
    @FXML
    private void searchAllPlayers() throws SQLException, ClassNotFoundException {
        try {
            //Get all red team Players information
            ObservableList<Player> redPlayerData = PersistenceHandler.searchRedPlayers();
            //Populate red team Players on TableView
            populateRedPlayers(redPlayerData);

            //Get all green team Players information
            ObservableList<Player> greenPlayerData = PersistenceHandler.searchGreenPlayers();
            //Populate green team Players on TableView
            populateGreenPlayers(greenPlayerData);

        } catch (SQLException e){
            System.out.println("Error occurred while getting players information from DB.\n" + e);
            throw e;
        }
    }
    //Populate Player
    @FXML
    private void populatePlayer (Player player) throws ClassNotFoundException {
        //Declare and ObservableList for table view
        ObservableList<Player> redTeamData = FXCollections.observableArrayList();
        //Add player to the ObservableList
        redTeamData.add(player);
        //Set items to the Table
        RedTeamTable.setItems(redTeamData);
    }

    //Populate Red team Players for TableView
    @FXML
    private void populateRedPlayers (ObservableList<Player> redTeamData) throws ClassNotFoundException {
        //Set items to the red_team Table
        RedTeamTable.setItems(redTeamData);
    }

    //Populate Green team Players for TableView
    @FXML
    private void populateGreenPlayers (ObservableList<Player> greenTeamData) throws ClassNotFoundException {
        //Set items to the green_team Table
        GreenTeamTable.setItems(greenTeamData);
    }

    public GameActionController() {
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

<<<<<<< HEAD
}
=======
}
>>>>>>> 763f6ec0c211bf09eff1dbbcc74c608fb66e4e1c
