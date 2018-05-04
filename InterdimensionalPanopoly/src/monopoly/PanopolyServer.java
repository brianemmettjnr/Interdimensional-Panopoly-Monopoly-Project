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
    static ArrayList<Socket> clientSockets;
    static ServerSocket serverSocket;
    private static int numLocations = 4 * ThreadLocalRandom.current().nextInt(8, 14 + 1);
    public static final Panopoly panopoly = new Panopoly(numLocations);

    void BeginServer() throws Exception {
        players=new ArrayList<>();
        System.out.println("Starting server...");
        serverSocket = new ServerSocket(7777);
        int i=0;

        while (true) {

            System.out.println("Server Started");
            Socket socket = serverSocket.accept();
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            if(players.size()!=maxNumOfPlayers){
                System.out.println("connection from: " + socket.getInetAddress());

                players.add(new Player("Player"+i, i, i, panopoly));
                Thread thread = new Thread(players.get(i));
                i++;
                thread.start();

            }
            if(players.size()==maxNumOfPlayers){

//                for (Socket s: clientSockets) {
//                    System.out.println("setupGUI");
//                    System.out.println("Making Panopoly");
//                    out.writeChar('f');
//                    System.out.println("panopoly object made");
//                }

                SetupGUI.PlayerNameServerGUI(panopoly,players);
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
