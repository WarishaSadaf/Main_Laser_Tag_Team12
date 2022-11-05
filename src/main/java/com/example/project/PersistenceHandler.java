/*
 * Overview: Implementation for the Persistence Handler class. Handles
 *           database connections and manages the flow of data in the Java application.

 * @version 1.0
 * @since 09/09/22
 * */

package com.example.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class PersistenceHandler extends PlayerPersistenceInterface {

    private static PersistenceHandler instance;

    //Database Credentials/Connection details

    private String url = "ec2-54-174-31-7.compute-1.amazonaws.com";
    private final String username = "inambnipuqgeeb";
    private final String password = "32f4a6737e7836883af199972e5730472321154383d0561466908eefac20d063";
    private int port = 5432;
    private String databaseName = "dc62tnlursroj2";

    private static Connection connection = null;

    private PersistenceHandler(){
        initializePostgresqlDatabase();
    }

    public static PersistenceHandler getInstance(){
        if (instance == null) {
            instance = new PersistenceHandler();
        }
        return instance;
    }

/*
 * Initializes data base connection
 */

    private void initializePostgresqlDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://" + url + ":" + port + "/" + databaseName, username, password);
            System.out.println("DB connected");
        } catch (SQLException | IllegalArgumentException | ClassNotFoundException ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (connection == null) {
                System.exit(-1);
            }
        }
    }

    /*
     * Retrieves a player's data from database
     * @return a String containing the player's name and ID
     * @return NULL if no record found
     */
    @Override
    //public List<Player> getPlayers() {
    public String getPlayers() {
        String sql = "SELECT id, codename "
                + "FROM player "
                + "WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet sqlReturnValues = stmt.executeQuery();

            if (sqlReturnValues != null){
                return sqlReturnValues.toString();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    /*
     * Adds a player's data to database in red_team Table
     * @param player The player object containing data to be stored
     * @return FALSE if data insertion failed
     * @return TRUE if data insertion was successful
     */
    @Override
    public boolean createRedPlayer(Player player)  {
        try {
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO red_team (id, codename) VALUES (?,?);");
                insertStatement.setInt(1, player.getId());
                insertStatement.setString(2, player.getCodename());
                insertStatement.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    /*
     * Adds a player's data to database in green_team Table
     * @param player The player object containing data to be stored
     * @return FALSE if data insertion failed
     * @return TRUE if data insertion was successful
     */
    @Override
    public boolean createGreenPlayer(Player player)  {
        try {
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO green_team (id, codename) VALUES (?,?);");
            insertStatement.setInt(1, player.getId());
            insertStatement.setString(2, player.getCodename());
            insertStatement.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }



    //Use ResultSet from DB as parameter and set Employee Object's attributes and return player object.
    private static Player getPlayerFromResultSet(ResultSet rs) throws SQLException
    {
        Player player = null;
        if (rs.next()) {
            player = new Player();
            player.setId(rs.getInt("id"));
            player.setCodename(rs.getString("codename"));
        }
        return player;
    }

    //*************************************
    //SELECT Players from red_team table
    //************************************
    public static ObservableList<Player> searchRedPlayers() throws SQLException, ClassNotFoundException {
        //Execute SELECT statement
        try {
            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT * FROM red_team;");
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsPlayers = selectStatement.executeQuery();
            //Send ResultSet to the getEmployeeList method and get player object
            ObservableList<Player> playerList = getPlayerList(rsPlayers);
            //Return player object
            return playerList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }


    //************************************
    // SELECT Players from green_team table
    //************************************
    public static ObservableList<Player> searchGreenPlayers() throws SQLException, ClassNotFoundException {
        //Execute SELECT statement
        try {
            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT * FROM green_team;");
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsPlayers = selectStatement.executeQuery();
            //Send ResultSet to the getEmployeeList method and get player object
            ObservableList<Player> playerList = getPlayerList(rsPlayers);
            //Return player object
            return playerList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }
    //Select * from red_team operation
    private static ObservableList<Player> getPlayerList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Employee objects
        ObservableList<Player> playerList = FXCollections.observableArrayList();
        while (rs.next()) {
            Player player = new Player();
            player.setId(rs.getInt("id"));
            player.setCodename(rs.getString("codename"));
            //Add player to the ObservableList
            playerList.add(player);
        }
        //return empList (ObservableList of Employees)
        return playerList;
    }
}
