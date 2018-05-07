//package monopoly;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.concurrent.ThreadLocalRandom;
//
//public class PanopolyServer {
//
//    public static ArrayList<Player> players;
//    static int maxNumOfPlayers = 2;
//    static ArrayList<Socket> clientSockets;
//    static ServerSocket serverSocket;
//    private static int numLocations = 4 * ThreadLocalRandom.current().nextInt(8, 14 + 1);
//    public static final Panopoly panopoly = new Panopoly(numLocations);
//
//    void BeginServer() throws Exception {
//        players=new ArrayList<>();
//        System.out.println("Starting server...");
//        serverSocket = new ServerSocket(7777);
//        System.out.println("Server Started");
//        ArrayList<DataOutputStream> outs = new ArrayList<>();
//        int i=0;
//
//        while (true) {
//
//            Socket socket = serverSocket.accept();
//
//            DataOutputStream out = new DataOutputStream(socket.getOutputStream());;
//            DataInputStream in = new DataInputStream(socket.getInputStream());
//
//            if(players.size()!=maxNumOfPlayers){
//                System.out.println("connection from: " + socket.getInetAddress());
//                out.write(i);
//                players.add(new NetworkedPlayer("Player"+i, i, i, panopoly,out,in));
//                Thread thread = new Thread(players.get(i));
//                thread.start();
//                i++;
//            }
//            if(players.size()==maxNumOfPlayers){
//
//                SetupGUI.PlayerNameServerGUI(panopoly,players);
////                for(int j = 0; j < outs.size(); j++) {
////                    System.out.println("sent f ");
////                    outs.get(j).writeChar('f');
////                    System.out.println("sent f 2 ");
////                }
//                System.out.println("full");
//                out.writeChar('f');
////                for (Socket s: clientSockets) {
////                    System.out.println("setupGUI");
////                    System.out.println("Making Panopoly");
////                    out.writeChar('f');
////                    System.out.println("panopoly object made");
////                }
//
//            }
////            for (int i = 0; i < maxNumOfPlayers; i++) {
////                if (players.get(i) == null) {
////                    System.out.println("connection from: " + socket.getInetAddress());
////                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
////                    DataInputStream in = new DataInputStream(socket.getInputStream());
////
////                    players.add(new Player("Player"+i, i, i, panopoly));
////                    Thread thread = new Thread(players.get(i));
////                    thread.start();
////                    break;
////                }
////
////            }
//        }
//
//    }
//    public static void main(String[] args) throws Exception {
//
//        PanopolyServer myGameServer = new PanopolyServer();
//        myGameServer.BeginServer();
//    }
//}
package monopoly;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PanopolyServer {

    public static ArrayList<Player> players;
    static int maxNumOfPlayers = 3;
    static Player[] ppl = new Player[maxNumOfPlayers];
    static ServerSocket serverSocket;
    private static int numLocations = 4 * ThreadLocalRandom.current().nextInt(8, 14 + 1);
    public static final Panopoly panopoly = new Panopoly(numLocations);

    void BeginServer() throws Exception {
        players=new ArrayList<>();
        ArrayList<DataOutputStream> outs = new ArrayList<>();
        System.out.println("Starting server...");
        serverSocket = new ServerSocket(7777);
        System.out.println("Server Started");
        int i=0;

        while (true) {
            Socket socket = serverSocket.accept();
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            if(players.size()!=maxNumOfPlayers){
                System.out.println("connection from: " + socket.getInetAddress());

                players.add(new Player("Player"+i, i, i, panopoly));
                //out.writeInt(i);
                outs.add(out);
                Thread thread = new Thread(players.get(i));
                i++;
                thread.start();

            }
            if(players.size()==maxNumOfPlayers){

//                for (Socket s: clientSockets) {
//                    System.out.println("setupGUI");
//                    System.out.println("Making Panopoly");
                SetupGUI.PlayerNameServerGUI(panopoly,players);
                for (int j = 0; j < outs.size(); j++) {
                    System.out.println("sent f");
                    outs.get(j).writeChar('f');
                    //outs.get(j).writeInt(j);
                }
//                System.out.println("sent f");
//                   out.writeChar('f');
//                    System.out.println("panopoly object made");
//                }


            }
//            for (int i = 0; i < maxNumOfPlayers; i++) {
//                if (players.get(i) == null) {
//                    System.out.println("connection from: " + socket.getInetAddress());
//                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//                    DataInputStream in = new DataInputStream(socket.getInputStream());
//
//                    players.add(new Player("Player"+i, i, i, panopoly));
//                    Thread thread = new Thread(players.get(i));
//                    thread.start();
//                    break;
//                }
//
//            }
        }

    }
    public static void main(String[] args) throws Exception {

        PanopolyServer myGameServer = new PanopolyServer();
        myGameServer.BeginServer();
    }
}