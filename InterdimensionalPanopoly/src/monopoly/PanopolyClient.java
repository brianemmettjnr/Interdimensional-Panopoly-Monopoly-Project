package monopoly;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class PanopolyClient {

    static DataInputStream in;
    static DataOutputStream out;
    static Socket socket;
    int playerId;
    JFrame clientFrame;

    public void makeClient(String serverAddress,int port) throws Exception {

        try {
            System.out.println("Connecting..");
            socket = new Socket(serverAddress, port);
            System.out.println("connected");
            in = new DataInputStream(socket.getInputStream());
            //playerId = in.readInt();
            out = new DataOutputStream(socket.getOutputStream());
            clientFrame = new JFrame("OK MATE");
            clientFrame.setVisible(true);
//            while(true){
//                if(in.readChar()=='f'){
//                    System.out.println("RECEIVED");
//
////                   SetupGUI.PlayerNameGUI(PanopolyServer.panopoly,PanopolyServer.players);
//                }
//            }


            //System.out.println("board");

        }catch(Exception e){
            System.out.println("Unable to start client");
        }
    }

    public static void main(String[] args) throws Exception {

        Scanner keyboard = new Scanner(System.in);
        System.out.print("Please enter the address of the Nim server (press enter if it is a localhost): ");
//        String serverAddress= keyboard.nextLine();
        String serverAddress = "localhost";
        if(serverAddress.equals("")) {
            serverAddress = "localhost";
        }
        int port=7777;
        PanopolyClient client = new PanopolyClient();
        client.makeClient(serverAddress,port);
    }
}
