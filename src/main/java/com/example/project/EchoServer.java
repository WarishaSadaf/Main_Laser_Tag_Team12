package com.example.project;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class EchoServer extends Thread {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];
    private String shooterCodename = "";
    private String targetCodename = "";
    private int shooterScore = 0;
    private int targetScore = 0;


    //global var to store green codename and individual score
    /*
     * id1: score_vale
     * id2: score_value
     *
     * */
    //global var red player codename and individual score
    /*
     * id1: score_vale
     * id2: score_value
     *
     * */
    public static String   to_print;

    PlayerPersistenceInterface persistenceHandler = PersistenceHandler.getInstance();

    public EchoServer() throws SocketException {
        socket = new DatagramSocket(7500);
    }

    public void run() {
        running = true;
        while (running) {

            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet); //check if there is any packet from the client
            } catch (IOException e) {
                e.printStackTrace();
            }

            //get client address and port
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            //get the packet data coming from that address and port (give as params to the new packet ob)
            packet = new DatagramPacket(buf, buf.length, address, port);
            //String received= new String(packet.getData(), 0, packet.getLength()); //convert packet to string
            String received = this.data(buf).toString().replaceAll(" ", "");
            System.out.println("\n[ LOG ] ----- Inside server run method ---received - "+ received );


            //if UDP protocol is not stopped, keep processing packets
            //TODO: strip garbage characters in received string
            if (!received.contains("end")) {
                System.out.println("[ LOG ] ----- Calling processData from server run method" );
                processData(received);
            }

            if (received.contains("end")) {
                running = false;
                //close the game
                System.exit(0);
            }

            try {
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        socket.close();

    }

    private String processData(String data){

        //TODO: fix bug of bytes getting part of the last string sent

        String[] dataChar = data.split(":");
        int shooter = Integer.parseInt(dataChar[0]);
        int target = Integer.parseInt(dataChar[1].trim());
        System.out.println("[ LOG ] ----- shooter "+ shooter + " target " + target + " <-- this pair must be equal to what the client sent, otherwise there's an error");


        //holds action msg
        String action = "";

        // If green player hits red player
        if(GameActionController.ShooterIsGreen) {
            if (searchInGreenTeamTable(shooter)) {
                if (searchInRedTeamTable(target)) {

                    //here's all data to be stored in an action event
                    shooterScore = computeGreenPlayerScore(shooter);
                    shooterCodename = searchCodenameInGreenTeamTable(shooter);
                    targetCodename = searchCodenameInRedTeamTable(target);
                    targetScore = computeRedPlayerScore(target);

                    action = shooterCodename + "---- hit ----" + targetCodename;

                    System.out.println("[ LOG ] ----- " + action);

                    System.out.println("[ LOG ] ----- SCORE: " + shooterCodename + " = " + shooterScore + " " + targetCodename + " = " + targetScore);
                    GameActionController.greenPlayerNameScore = shooterCodename + "    " +String.valueOf(shooterScore);

                    System.out.println("[ LOG ] ----- Green-HashMap update = " + GameActionController.GreenPlayerScores);
                    System.out.println("[ LOG ] ----- Red-HashMap update = " + GameActionController.RedPlayerScores);
                    Integer greenTeamScore = GameActionController.GreenPlayerScores.values().stream().mapToInt(d-> d).sum();
                    GameActionController.greenTotalScore = String.valueOf(greenTeamScore);
                    System.out.println("[ LOG ] ----- Green-HashMap update = greenTotalScore = "+GameActionController.greenTotalScore);
                    //TODO:

                    /*store
                    1. codenames/individual score
                    2. action msg "shooter_code_name hit target_code_name"
                    * */
                    GameActionController.setActions(action);

                    GameActionController.saveInfo();

                }
            }
        }



        // if red player hits green player
        if(GameActionController.ShooterIsRed) {
            if (searchInRedTeamTable(shooter)) {
                if (searchInGreenTeamTable(target)) {
                    shooterCodename = searchCodenameInRedTeamTable(shooter);
                    shooterScore = computeRedPlayerScore(shooter);
                    targetCodename = searchCodenameInGreenTeamTable(target);
                    targetScore = computeGreenPlayerScore(target);

                    action = shooterCodename + "---- hit ----" + targetCodename;
                    System.out.println("[ LOG ] ----- Action: " + action);
                    System.out.println("[ LOG ] ----- SCORE: " + shooterCodename + " = " + shooterScore +" "+ targetCodename + " = " + targetScore);
                    GameActionController.redPlayerNameScore = shooterCodename + "    " +String.valueOf(shooterScore);
                    //GameActionController.greenTeamNameScore.setText(shooterCodename + "    " +String.valueOf(shooterScore));


                    System.out.println("[ LOG ] ----- Green-HashMap update = " + GameActionController.GreenPlayerScores);
                    System.out.println("[ LOG ] ----- Red-HashMap update = " + GameActionController.RedPlayerScores);
                    Integer greenTeamScore = GameActionController.RedPlayerScores.values().stream().mapToInt(d-> d).sum();
                    GameActionController.redTotalScore = String.valueOf(greenTeamScore);
                    System.out.println("[ LOG ] ----- Red-HashMap update = redTotalScore = "+GameActionController.redTotalScore);

                    //TODO:
                    /*Store
                    1. codenames/individual score
                    2. action msg "shooter_code_name hit target_code_name"

                    * */
                    GameActionController.setActions(action);

                    GameActionController.saveInfo();

                }
            }
        }

        //clean up strings
        to_print = action;
        shooter = target = 0;

        return action;

    }

    private boolean searchInGreenTeamTable(int playerId ){

        ObservableList<Player> greenPlayer = PersistenceHandler.getGreenTeamPlayer(playerId);

        //to find a green player, iterate over the green player table and look for the specific id
        for(int i = 0; i < greenPlayer.size(); i++){
            if(greenPlayer.get(i).getId() == (playerId) )
            {

                return true;
            }
        } //if it gets to the end of the list and the id wasn't found, player doesn't exit
        System.out.println("[ LOG ] ----- player "+ playerId+" doesn't exist");
        return false;
    }

    private String searchCodenameInRedTeamTable(int playerId ){

        ObservableList<Player> redPlayer = PersistenceHandler.getRedTeamPlayer(playerId);

        //to find a red player, iterate over the green player table and look for the specific id
        for(int i = 0; i < redPlayer.size(); i++){
            if(redPlayer.get(i).getId() == (playerId) )
            {
                //codename
                String codename = redPlayer.get(i).getCodename();
                return codename;
            }
        } //if it gets to the end of the list and the id wasn't found, player doesn't exit
        return "Unknown";
    }

    private String searchCodenameInGreenTeamTable(int playerId ){

        ObservableList<Player> greenPlayer = PersistenceHandler.getGreenTeamPlayer(playerId);

        //to find a green player, iterate over the green player table and look for the specific id
        for(int i=0; greenPlayer.size()>i; i++){
            if(greenPlayer.get(i).getId() == (playerId) )
            {
                //codename
                String codename = greenPlayer.get(i).getCodename();
                return codename;
            }

        }//if it gets to the end of the list and the id wasn't found, player doesn't exit
        return "Unknown";
    }

    private boolean searchInRedTeamTable(int playerId ){
        ObservableList<Player> redPlayer = PersistenceHandler.getRedTeamPlayer(playerId);
        boolean flag = true;

        for(int i = 0; i < redPlayer.size(); i++){
            if(redPlayer.get(i).getId() == (playerId))
            {
                flag = true;
                //get player ID and codename here
            }
            else {
                flag = false;
                System.out.println("[ LOG ] ----- player "+ playerId+" doesn't exist");
            }
        }

        return flag;
    }

    private int computeGreenPlayerScore(int playerKey){
        int score = 0;
        if(GameActionController.GreenPlayerScores.get(playerKey) !=null){

            score = GameActionController.GreenPlayerScores.get(playerKey);
            /*determine if points are going to be added or subtracted
                    ShooterIsGreen = TRUE means adding points to this green shooter player
                    ShooterIsGreen = FALSE means subtracting points to this green target player

            */
            if (GameActionController.ShooterIsGreen) {
                //add ten points to current shooter score
                score += 10;
            } else {
                //this avoids scores from going negative
                if (score <= 10) {
                    score = 0;
                }
                //if current score allows to subtract 10 points
                else if (score > 10) {
                    score -= 10;
                }
            }

            //update value in hashMap
            GameActionController.GreenPlayerScores.replace(playerKey, score);
        }
        return  score;
    }

    private int computeRedPlayerScore(int playerKey){
        int score =0;
        if(GameActionController.RedPlayerScores.get(playerKey) != null) {
             score = GameActionController.RedPlayerScores.get(playerKey);
            /*determine if points are going to be added or subtracted
                    ShooterIsRed = TRUE means adding points to this red shooter player
                    ShooterIsRed = FALSE means subtracting points to this red target player

            */
            if (GameActionController.ShooterIsRed) {
                //add ten points to current shooter score
                score += 10;
            } else {
                //this avoids scores from going negative
                if (score <= 10) {
                    score = 0;
                }
                //if current score allows to subtract 10 points
                else if (score > 10) {
                    score -= 10;
                }
            }

            //update value in hashMap
            GameActionController.RedPlayerScores.replace(playerKey, score);
        }
        return  score;
    }

    // A utility method to convert the byte array
    // data into a string representation.
    private StringBuilder data(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }

}