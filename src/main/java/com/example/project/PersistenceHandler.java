package com.example.project;

import com.example.project.Player;
import com.example.project.PlayerPersistenceInterface;

import java.sql.*;


public class PersistenceHandler extends PlayerPersistenceInterface {

    private static PersistenceHandler instance;

    //Database Connection details
    //private String url = "localhost";
    private String url = "ec2-54-174-31-7.compute-1.amazonaws.com";
    private final String username = "inambnipuqgeeb";
    private final String password = "32f4a6737e7836883af199972e5730472321154383d0561466908eefac20d063";
    private int port = 5432;
    private String databaseName = "dc62tnlursroj2";

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