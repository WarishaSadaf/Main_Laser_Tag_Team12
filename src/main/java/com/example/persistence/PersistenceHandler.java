package com.example.persistence;

import com.example.domain.Player;
import com.example.domain.PlayerPersistenceInterface;

import java.sql.*;


public class PersistenceHandler implements PlayerPersistenceInterface {


    private static PersistenceHandler instance;

    //Database Connection details
    //private String url = "localhost";
    private String url = "ec2-54-147-36-107.compute-1.amazonaws.com";
    private final String username = "dspssnbvycuijo";
    private final String password = "9a16154741e5bcd0cd1d30db6f682ee0a696467eb62cc6004e5a5f1fa1ab3123";
    private int port = 5432;
    private String databaseName = "dcb2582rruc9sg";

    private Connection connection = null;

    private PersistenceHandler(){
        initializePostgresqlDatabase();
    }

    public static PersistenceHandler getInstance(){
        if (instance == null) {
            instance = new PersistenceHandler();
        }
        return instance;
    }

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

    @Override
    public boolean createPlayer(Player player) {
        try {
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO player (id, codename) VALUES (?,?);");
            insertStatement.setString(1, player.getId());
            insertStatement.setString(2,player.getCodename());

            insertStatement.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }
}